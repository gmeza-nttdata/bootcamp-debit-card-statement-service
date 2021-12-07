package com.nttdata.bootcamp.debitcardstatementservice.domain;

import com.nttdata.bootcamp.debitcardstatementservice.domain.dto.OperationType;
import com.nttdata.bootcamp.debitcardstatementservice.domain.entity.Account;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DebitCardStatement {
    private String id;
    private String productType;
    private String number;
    private OperationType operation;
    private String cardId;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private BigDecimal fee;

    public DebitCardStatement() {}

    public DebitCardStatement(Account updated, OperationType operation, BigDecimal amount, BigDecimal fee, String cardId) {
        this.productType = updated.getType();
        this.number = updated.getNumber();
        this.dateTime = LocalDateTime.now();
        this.operation = operation;
        this.amount = amount;
        this.fee = fee;
        this.cardId = cardId;
    }

    public DebitCardStatement(String toString) {
        this.id = toString;
    }
}
