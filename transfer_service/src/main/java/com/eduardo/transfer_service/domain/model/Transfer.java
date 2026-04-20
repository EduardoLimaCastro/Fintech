package com.eduardo.transfer_service.domain.model;

import com.eduardo.transfer_service.domain.enums.TransferStatus;
import com.eduardo.transfer_service.domain.exceptions.InvalidTransferStateTransitionException;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transfer {

    private UUID id;
    private UUID sourceAccountId;
    private UUID targetAccountId;
    private BigDecimal amount;
    private TransferStatus status;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Transfer(UUID id, UUID sourceAccountId, UUID targetAccountId,
                     BigDecimal amount, TransferStatus status, String failureReason,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.status = status;
        this.failureReason = failureReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Transfer create(UUID sourceAccountId, UUID targetAccountId,
                                   BigDecimal amount, Clock clock) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Transfer amount must be positive.");
        if (sourceAccountId == null || targetAccountId == null)
            throw new IllegalArgumentException("Source and target account IDs are required.");
        if (sourceAccountId.equals(targetAccountId))
            throw new IllegalArgumentException("Source and target accounts must be different.");

        LocalDateTime now = LocalDateTime.now(clock);
        return new Transfer(UUID.randomUUID(), sourceAccountId, targetAccountId,
                amount, TransferStatus.PENDING, null, now, now);
    }

    public static Transfer reconstitute(UUID id, UUID sourceAccountId, UUID targetAccountId,
                                         BigDecimal amount, TransferStatus status,
                                         String failureReason, LocalDateTime createdAt,
                                         LocalDateTime updatedAt) {
        return new Transfer(id, sourceAccountId, targetAccountId,
                amount, status, failureReason, createdAt, updatedAt);
    }

    public void markDebited(Clock clock) {
        changeStatus(TransferStatus.DEBITED, null, clock);
    }

    public void markCompleted(Clock clock) {
        changeStatus(TransferStatus.COMPLETED, null, clock);
    }

    public void markCompensating(String reason, Clock clock) {
        changeStatus(TransferStatus.COMPENSATING, reason, clock);
    }

    public void markFailed(String reason, Clock clock) {
        changeStatus(TransferStatus.FAILED, reason, clock);
    }

    private void changeStatus(TransferStatus newStatus, String reason, Clock clock) {
        if (!this.status.canTransitionTo(newStatus)) {
            throw new InvalidTransferStateTransitionException(this.id, this.status, newStatus);
        }
        this.status = newStatus;
        this.failureReason = reason;
        this.updatedAt = LocalDateTime.now(clock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfer other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public UUID getId()               { return id; }
    public UUID getSourceAccountId()  { return sourceAccountId; }
    public UUID getTargetAccountId()  { return targetAccountId; }
    public BigDecimal getAmount()     { return amount; }
    public TransferStatus getStatus() { return status; }
    public String getFailureReason()  { return failureReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
