package com.nttdata.bootcamp.debitcardstatementservice.domain.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class DebitCard {
    private String id;
    private Long number;
    private String mainAccount;

    private LocalDate expirationDate;
    private String cvv;

    private ArrayList<String> accounts;
}

