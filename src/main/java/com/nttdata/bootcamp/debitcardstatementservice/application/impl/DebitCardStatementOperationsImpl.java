package com.nttdata.bootcamp.debitcardstatementservice.application.impl;

import com.nttdata.bootcamp.debitcardstatementservice.application.DebitCardStatementOperations;
import com.nttdata.bootcamp.debitcardstatementservice.application.repository.StatementRepository;
import com.nttdata.bootcamp.debitcardstatementservice.application.service.AccountService;
import com.nttdata.bootcamp.debitcardstatementservice.application.service.DebitCardService;
import com.nttdata.bootcamp.debitcardstatementservice.application.service.UserService;
import com.nttdata.bootcamp.debitcardstatementservice.domain.DebitCardStatement;
import com.nttdata.bootcamp.debitcardstatementservice.domain.dto.OperationType;
import com.nttdata.bootcamp.debitcardstatementservice.domain.entity.Account;
import com.nttdata.bootcamp.debitcardstatementservice.domain.entity.DebitCard;
import com.nttdata.bootcamp.debitcardstatementservice.infrastructure.model.dto.OperationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebitCardStatementOperationsImpl implements DebitCardStatementOperations {
    private final StatementRepository repository;
    private final AccountService accountService;
    private final DebitCardService debitCardService;


    @Override
    public Mono<DebitCardStatement> payWithDebitCard(String cardId, BigDecimal amount) {
        return prepareTransaction(debitCardService.get(cardId), OperationType.PAYMENT, cardId, amount);
    }

    @Override
    public Mono<DebitCardStatement> withdraw(String cardId, BigDecimal amount) {
        return prepareTransaction(debitCardService.get(cardId), OperationType.PAYMENT, cardId, amount);
    }

    private Mono<DebitCardStatement> prepareTransaction(Mono<DebitCard> debitCardMono, OperationType operationType,
                                                        String cardId, BigDecimal amount) {
        return debitCardMono.map(DebitCard::getMainAccount)
                .flatMap(accountService::get)
                .flatMap(mainAccount -> {
                    BigDecimal fee = calculateFee(mainAccount);
                    if (!canAchieveTransaction(mainAccount, amount, fee)) {
                        return debitCardMono.flatMapIterable(DebitCard::getAccounts)
                                .filter(accountNumber -> !accountNumber.equals(mainAccount.getNumber()))
                                .flatMap(accountService::get)
                                .filter(account -> canAchieveTransaction(account, amount, fee))
                                .collectList()
                                .flatMap(accounts -> {
                                    if (accounts.isEmpty())
                                        return Mono.error(new IllegalArgumentException("Not enough balance to achieve transaction"));
                                    return doTransaction(accounts.get(0), amount, fee, operationType, cardId);
                                });
                    }
                    return doTransaction(mainAccount, amount, fee, operationType, cardId);
                });
    }

    @Override
    public Flux<DebitCardStatement> getStatementsByAccountNumber(String accountNumber) {
        return repository.queryAll()
                .filter(s -> s.getNumber().equals(accountNumber));
    }

    @Override
    public Flux<DebitCardStatement> getAllStatements() {
        return repository.queryAll();
    }

    @Override
    public Mono<DebitCardStatement> getStatement(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.delete(id);
    }

    private BigDecimal getFinalBalance(Account account, BigDecimal amount, BigDecimal fee) {
        return account.getBalance()
                .subtract(amount)
                .subtract(fee);
    }

    private BigDecimal calculateFee(Account account) {
        return BigDecimal.ZERO;
    }

    private boolean canAchieveTransaction(Account account, BigDecimal amount, BigDecimal fee) {
        BigDecimal finalBalance = getFinalBalance(account, amount, fee);
        return finalBalance.compareTo(BigDecimal.ZERO) >= 0;
    }

    private Mono<DebitCardStatement> doTransaction(Account account, BigDecimal amount, BigDecimal fee, OperationType operation, String cardId) {
        account.setBalance(getFinalBalance(account, amount, fee));
        return accountService.update(account.getNumber(), account)
                .flatMap(updated -> repository.create(
                        new DebitCardStatement(updated,
                                operation,
                                amount,
                                fee,
                                cardId
                                )
                ));
    }

}
