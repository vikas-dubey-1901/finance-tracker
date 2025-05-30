package com.vikas.financetracker.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Month;

@Data
@Builder
public class MonthlySpendingSummary {
    private Month month;
    private int year;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
}

