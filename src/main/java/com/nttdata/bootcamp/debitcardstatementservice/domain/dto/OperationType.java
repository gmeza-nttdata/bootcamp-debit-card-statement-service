package com.nttdata.bootcamp.debitcardstatementservice.domain.dto;

public enum OperationType {
    // For Account:
    DEPOSIT, WITHDRAWAL,
    // For Credit:
    CONSUMPTION, PAYMENT,
    // For Debit:
    DEBIT_CONSUMPTION
}
