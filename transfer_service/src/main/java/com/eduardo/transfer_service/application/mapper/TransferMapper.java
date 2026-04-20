package com.eduardo.transfer_service.application.mapper;

import com.eduardo.transfer_service.application.dto.request.CreateTransferRequest;
import com.eduardo.transfer_service.application.dto.response.TransferResponse;
import com.eduardo.transfer_service.domain.model.Transfer;

import java.time.Clock;
import java.util.UUID;

public final class TransferMapper {

    private TransferMapper() {}

    public static Transfer toDomain(CreateTransferRequest request, Clock clock) {
        return Transfer.create(
                UUID.fromString(request.sourceAccountId()),
                UUID.fromString(request.targetAccountId()),
                request.amount(),
                clock
        );
    }

    public static TransferResponse toResponse(Transfer transfer) {
        return new TransferResponse(
                transfer.getId(),
                transfer.getSourceAccountId(),
                transfer.getTargetAccountId(),
                transfer.getAmount(),
                transfer.getStatus(),
                transfer.getFailureReason(),
                transfer.getCreatedAt(),
                transfer.getUpdatedAt()
        );
    }
}
