package com.nttdata.bootcamp.debitcardstatementservice.domain;

import com.nttdata.bootcamp.debitcardstatementservice.domain.dto.OperationType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DebitCardStatement {
    private String id;
    private String productType;
    private String number;
    private OperationType operation;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private BigDecimal fee;
}
