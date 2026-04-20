package com.eduardo.transaction_service.application.filter;

import com.eduardo.transaction_service.domain.enums.TransactionStatus;
import com.eduardo.transaction_service.domain.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionFilter(
        UUID accountId,
        TransactionType type,
        TransactionStatus status,
        LocalDateTime createdAfter,
        LocalDateTime createdBefore
) {}
