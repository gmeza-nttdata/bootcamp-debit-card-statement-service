package com.nttdata.bootcamp.debitcardstatementservice.infrastructure.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationData {
    private String cardId;
    private BigDecimal amount;
}
