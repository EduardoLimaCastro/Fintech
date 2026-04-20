package com.eduardo.transaction_service.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TransactionJpaRepository
        extends JpaRepository<TransactionJpaEntity, UUID>,
                JpaSpecificationExecutor<TransactionJpaEntity> {
}
