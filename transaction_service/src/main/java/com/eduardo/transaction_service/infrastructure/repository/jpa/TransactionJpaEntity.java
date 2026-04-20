package com.eduardo.transaction_service.infrastructure.repository.jpa;

import com.eduardo.transaction_service.domain.enums.TransactionStatus;
import com.eduardo.transaction_service.domain.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor
public class TransactionJpaEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "related_account_id")
    private UUID relatedAccountId;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;

    public TransactionJpaEntity(
            UUID id,
            TransactionType type,
            BigDecimal amount,
            UUID accountId,
            UUID relatedAccountId,
            String description,
            TransactionStatus status,
            LocalDateTime createdAt,
            Long version
    ) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
        this.relatedAccountId = relatedAccountId;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.version = version;
    }
}
