package com.nttdata.bootcamp.debitcardstatementservice.infrastructure.rest;

import com.nttdata.bootcamp.debitcardstatementservice.application.DebitCardStatementOperations;
import com.nttdata.bootcamp.debitcardstatementservice.domain.DebitCardStatement;
import com.nttdata.bootcamp.debitcardstatementservice.infrastructure.model.dto.OperationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/debit-card-statements")
@RequiredArgsConstructor
public class DebitCardStatementController {

    private final DebitCardStatementOperations operations;

    @PostMapping(value = "pay", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DebitCardStatement>> payWithDebitCard(@RequestBody OperationData data) {
        return operations.payWithDebitCard(data.getCardId(), data.getAmount())
                .doOnError(throwable -> log.error(throwable.toString()))
                .map(ResponseEntity::ok)
                .onErrorResume(throwable ->
                        Mono.just(ResponseEntity.badRequest().body(new DebitCardStatement(throwable.toString()))));
    }

    @PostMapping(value = "withdraw", produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DebitCardStatement>> withdraw(@RequestBody OperationData data) {
        return operations.withdraw(data.getCardId(), data.getAmount())
                .doOnError(throwable -> log.error(throwable.toString()))
                .map(ResponseEntity::ok)
                .onErrorResume(throwable ->
                        Mono.just(ResponseEntity.badRequest().body(new DebitCardStatement(throwable.toString()))));
    }

    @GetMapping(value = "{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DebitCardStatement>>> getStatementsByAccountNumber(@PathVariable String accountNumber) {
        return Mono.just(ResponseEntity.ok(operations.getAllStatements()
            .filter(debitCardStatement -> debitCardStatement.getNumber().equals(accountNumber))
        ));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DebitCardStatement>>> getAllStatements() {
        return Mono.just(ResponseEntity.ok(operations.getAllStatements()));
    }

    @DeleteMapping(value = "{id}")
    public Mono<ResponseEntity<Void>> delete(String id) {
        return operations.delete(id)
                .thenReturn(new
                        ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
