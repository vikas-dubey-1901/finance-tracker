package com.vikas.financetracker.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CategorySpending {
    private String category;
    private BigDecimal totalSpent;
}

