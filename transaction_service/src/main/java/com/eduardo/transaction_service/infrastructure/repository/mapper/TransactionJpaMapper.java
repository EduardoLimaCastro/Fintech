package com.eduardo.transaction_service.infrastructure.repository.mapper;

import com.eduardo.transaction_service.domain.model.Transaction;
import com.eduardo.transaction_service.infrastructure.repository.jpa.TransactionJpaEntity;

public final class TransactionJpaMapper {

    private TransactionJpaMapper() {}

    public static TransactionJpaEntity toEntity(Transaction domain) {
        return new TransactionJpaEntity(
                domain.getId(),
                domain.getType(),
                domain.getAmount(),
                domain.getAccountId(),
                domain.getRelatedAccountId(),
                domain.getDescription(),
                domain.getStatus(),
                domain.getCreatedAt(),
                domain.getVersion()
        );
    }

    public static Transaction toDomain(TransactionJpaEntity entity) {
        return Transaction.reconstitute(
                entity.getId(),
                entity.getType(),
                entity.getAmount(),
                entity.getAccountId(),
                entity.getRelatedAccountId(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getVersion()
        );
    }
}
