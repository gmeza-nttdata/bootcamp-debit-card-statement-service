package com.nttdata.bootcamp.debitcardstatementservice.application.impl;

import com.nttdata.bootcamp.debitcardstatementservice.application.DebitCardStatementOperations;
import com.nttdata.bootcamp.debitcardstatementservice.application.repository.StatementRepository;
import com.nttdata.bootcamp.debitcardstatementservice.application.service.AccountService;
import com.nttdata.bootcamp.debitcardstatementservice.application.service.UserService;
import com.nttdata.bootcamp.debitcardstatementservice.domain.DebitCardStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebitCardStatementOperationsImpl implements DebitCardStatementOperations {
    private final StatementRepository repository;
    private final AccountService accountService;
    private final UserService userService;


    @Override
    public Flux<DebitCardStatement> queryAll() {
        return null;
    }

    @Override
    public Mono<DebitCardStatement> findById(String id) {
        return null;
    }

    @Override
    public Mono<DebitCardStatement> create(DebitCardStatement accountStatement) {
        return null;
    }

    @Override
    public Mono<DebitCardStatement> update(String id, DebitCardStatement accountStatement) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id) {
        return null;
    }
}
