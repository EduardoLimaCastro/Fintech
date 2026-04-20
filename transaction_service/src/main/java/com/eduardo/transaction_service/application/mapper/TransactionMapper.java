package com.eduardo.transaction_service.application.mapper;

import com.eduardo.transaction_service.application.dto.CreateTransactionRequest;
import com.eduardo.transaction_service.application.dto.TransactionResponse;
import com.eduardo.transaction_service.domain.model.Transaction;

import java.time.Clock;

public final class TransactionMapper {

    private TransactionMapper() {}

    public static Transaction toDomain(CreateTransactionRequest request, Clock clock) {
        return Transaction.create(
                request.type(),
                request.amount(),
                request.accountId(),
                request.relatedAccountId(),
                request.description(),
                request.status(),
                clock
        );
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getAccountId(),
                transaction.getRelatedAccountId(),
                transaction.getDescription(),
                transaction.getStatus(),
                transaction.getCreatedAt(),
                transaction.getVersion()
        );
    }
}
