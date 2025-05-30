package com.vikas.financetracker.dto;

import com.example.reactivefinancetracker.domain.Transaction.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class TransactionResponse {

    private String id;

    private String category;

    private BigDecimal amount;

    private Instant timestamp;

    private String description;

    private TransactionType type;

}

