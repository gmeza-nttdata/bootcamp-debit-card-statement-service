package com.nttdata.bootcamp.debitcardstatementservice.infrastructure.repository;

import com.nttdata.bootcamp.debitcardstatementservice.application.repository.StatementRepository;
import com.nttdata.bootcamp.debitcardstatementservice.domain.DebitCardStatement;
import com.nttdata.bootcamp.debitcardstatementservice.infrastructure.model.dao.DebitCardStatementDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StatementCrudRepository implements StatementRepository {

    @Autowired
    IStatementCrudRepository repository;


    @Override
    public Flux<DebitCardStatement> queryAll() {
        return repository.findAll()
                .map(this::mapStatementDaoToStatement);
    }

    @Override
    public Mono<DebitCardStatement> findById(String id) {
        return repository.findById(id)
                .map(this::mapStatementDaoToStatement);
    }

    @Override
    public Mono<DebitCardStatement> create(DebitCardStatement accountStatement) {
        return Mono.just(accountStatement)
                .doOnNext(s -> s.setId(null))
                .map(this::mapStatementToStatementDao)
                .flatMap(repository::save)
                .map(this::mapStatementDaoToStatement);
    }

    @Override
    public Mono<DebitCardStatement> update(String id, DebitCardStatement accountStatement) {
        return repository.findById(id)
                .flatMap(s -> {
                    s.setId(id);
                    return Mono.just(accountStatement)
                            .map(this::mapStatementToStatementDao);
                }).flatMap(repository::save)
                .map(this::mapStatementDaoToStatement);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    private DebitCardStatement mapStatementDaoToStatement(DebitCardStatementDao accountStatementDao) {
        DebitCardStatement accountStatement = new DebitCardStatement();
        BeanUtils.copyProperties(accountStatementDao, accountStatement);
        return accountStatement;
    }

    private DebitCardStatementDao mapStatementToStatementDao(DebitCardStatement accountStatement) {
        DebitCardStatementDao accountStatementDao = new DebitCardStatementDao();
        BeanUtils.copyProperties(accountStatement, accountStatementDao);
        return accountStatementDao;
    }


}
