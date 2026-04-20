package com.eduardo.transaction_service.application.port.out;

import com.eduardo.transaction_service.application.filter.TransactionFilter;
import com.eduardo.transaction_service.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryPort {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(UUID id);
    Page<Transaction> list(TransactionFilter filter, Pageable pageable);
}
