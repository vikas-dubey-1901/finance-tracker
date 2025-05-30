package com.vikas.financetracker.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String userId;  // reference to user who owns this transaction

    private String category; // e.g., Food, Rent, Utilities

    private BigDecimal amount;

    private Instant timestamp;

    private String description;

    private TransactionType type;  // EXPENSE or INCOME

    public enum TransactionType {
        EXPENSE,
        INCOME
    }
}

