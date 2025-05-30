package com.vikas.financetracker.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BudgetResponse {
    private String category;
    private int year;
    private int month;
    private BigDecimal budgeted;
    private BigDecimal spent;
}

