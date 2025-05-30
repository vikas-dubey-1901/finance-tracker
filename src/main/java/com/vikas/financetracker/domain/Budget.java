package com.vikas.financetracker.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "budgets")
public class Budget {

    @Id
    private String id;

    private String userId;

    private String category;

    private int year;

    private int month; // 1-12

    private BigDecimal amount;
}

