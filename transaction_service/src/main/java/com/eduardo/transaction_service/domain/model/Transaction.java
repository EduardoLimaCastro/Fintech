package com.eduardo.transaction_service.domain.model;

import com.eduardo.transaction_service.domain.enums.TransactionStatus;
import com.eduardo.transaction_service.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Registro financeiro imutável — sem métodos de alteração por design.
 * Uma vez criada, uma transação não pode ser editada (requisito regulatório).
 * Reversões são representadas por uma nova transação do tipo REFUND.
 */
public class Transaction {

    private final UUID id;
    private final TransactionType type;
    private final BigDecimal amount;
    private final UUID accountId;
    private final UUID relatedAccountId;
    private final String description;
    private final TransactionStatus status;
    private final LocalDateTime createdAt;
    private final Long version;

    private Transaction(
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

    // =========================
    // Factory methods
    // =========================

    public static Transaction create(
            TransactionType type,
            BigDecimal amount,
            UUID accountId,
            UUID relatedAccountId,
            String description,
            TransactionStatus status,
            Clock clock
    ) {
        validate(type, amount, accountId, status);

        return new Transaction(
                UUID.randomUUID(),
                type,
                amount,
                accountId,
                relatedAccountId,
                description,
                status,
                LocalDateTime.now(clock),
                null
        );
    }

    public static Transaction reconstitute(
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
        return new Transaction(id, type, amount, accountId, relatedAccountId,
                description, status, createdAt, version);
    }

    // =========================
    // Validation
    // =========================

    private static void validate(
            TransactionType type,
            BigDecimal amount,
            UUID accountId,
            TransactionStatus status
    ) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type is required");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("Transaction status is required");
        }
    }

    // =========================
    // Identity
    // =========================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // =========================
    // Getters
    // =========================

    public UUID getId() { return id; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public UUID getAccountId() { return accountId; }
    public UUID getRelatedAccountId() { return relatedAccountId; }
    public String getDescription() { return description; }
    public TransactionStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getVersion() { return version; }
}
