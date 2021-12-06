package com.nttdata.bootcamp.debitcardstatementservice.infrastructure.model.dao;

import com.nttdata.bootcamp.debitcardstatementservice.domain.dto.OperationType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document("debit-card-statement")
public class DebitCardStatementDao {
    private String id;
    private String productType;
    private String number;
    private OperationType operation;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private BigDecimal fee;
}
