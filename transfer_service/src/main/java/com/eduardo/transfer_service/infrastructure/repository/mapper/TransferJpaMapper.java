package com.eduardo.transfer_service.infrastructure.repository.mapper;

import com.eduardo.transfer_service.domain.model.Transfer;
import com.eduardo.transfer_service.infrastructure.repository.jpa.TransferJpaEntity;

public final class TransferJpaMapper {

    private TransferJpaMapper() {}

    public static TransferJpaEntity toEntity(Transfer transfer) {
        return new TransferJpaEntity(
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

    public static Transfer toDomain(TransferJpaEntity entity) {
        return Transfer.reconstitute(
                entity.getId(),
                entity.getSourceAccountId(),
                entity.getTargetAccountId(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getFailureReason(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
