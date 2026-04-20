package com.eduardo.transfer_service.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransferRequest(
        @NotNull(message = "sourceAccountId is required")
        String sourceAccountId,

        @NotNull(message = "targetAccountId is required")
        String targetAccountId,

        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be greater than zero")
        BigDecimal amount
) {}
