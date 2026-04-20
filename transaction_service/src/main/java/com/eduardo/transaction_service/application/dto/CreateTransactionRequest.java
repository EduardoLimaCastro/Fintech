package com.eduardo.transaction_service.application.dto;

import com.eduardo.transaction_service.domain.enums.TransactionStatus;
import com.eduardo.transaction_service.domain.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotNull TransactionType type,
        @NotNull @DecimalMin(value = "0.01", message = "Amount must be positive") BigDecimal amount,
        @NotNull UUID accountId,
        UUID relatedAccountId,
        String description,
        @NotNull TransactionStatus status
) {}
