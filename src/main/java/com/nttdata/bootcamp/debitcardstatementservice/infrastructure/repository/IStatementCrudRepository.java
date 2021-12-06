package com.nttdata.bootcamp.debitcardstatementservice.infrastructure.repository;

import com.nttdata.bootcamp.debitcardstatementservice.infrastructure.model.dao.DebitCardStatementDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IStatementCrudRepository extends ReactiveCrudRepository<DebitCardStatementDao, String> {
}
