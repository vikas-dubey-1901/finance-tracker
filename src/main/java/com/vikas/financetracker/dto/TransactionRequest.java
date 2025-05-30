package com.vikas.financetracker.dto;

import com.vikas.financetracker.domain.Transaction.TransactionType;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotBlank
    private String category;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal amount;

    @NotNull
    private TransactionType type;

    private String description;

}

