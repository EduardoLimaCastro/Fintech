package com.eduardo.transaction_service.application.dto;

import com.eduardo.transaction_service.domain.enums.TransactionStatus;
import com.eduardo.transaction_service.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        TransactionType type,
        BigDecimal amount,
        UUID accountId,
        UUID relatedAccountId,
        String description,
        TransactionStatus status,
        LocalDateTime createdAt,
        Long version
) {}
