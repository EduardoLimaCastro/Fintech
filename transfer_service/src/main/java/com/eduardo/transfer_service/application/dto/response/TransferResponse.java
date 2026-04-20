package com.eduardo.transfer_service.application.dto.response;

import com.eduardo.transfer_service.domain.enums.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferResponse(
        UUID id,
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal amount,
        TransferStatus status,
        String failureReason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
