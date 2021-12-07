package com.nttdata.bootcamp.debitcardstatementservice.application;

import com.nttdata.bootcamp.debitcardstatementservice.domain.DebitCardStatement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface DebitCardStatementOperations {

    Mono<DebitCardStatement> payWithDebitCard(String cardId, BigDecimal amount);
    Mono<DebitCardStatement> withdraw(String cardId, BigDecimal amount);

    Flux<DebitCardStatement> getStatementsByAccountNumber(String accountNumber);
    Flux<DebitCardStatement> getAllStatements();

    Mono<DebitCardStatement> getStatement(String id);
    Mono<Void> delete(String id);

}
