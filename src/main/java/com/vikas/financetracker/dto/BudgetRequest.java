package com.vikas.financetracker.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BudgetRequest {
    @NotBlank
    private String category;

    @NotNull
    private Integer year;

    @NotNull
    private Integer month;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
}

