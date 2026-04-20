package com.eduardo.transaction_service.infrastructure.repository.adapter;

import com.eduardo.transaction_service.application.filter.TransactionFilter;
import com.eduardo.transaction_service.application.port.out.TransactionRepositoryPort;
import com.eduardo.transaction_service.domain.model.Transaction;
import com.eduardo.transaction_service.infrastructure.repository.jpa.TransactionJpaRepository;
import com.eduardo.transaction_service.infrastructure.repository.jpa.TransactionSpecification;
import com.eduardo.transaction_service.infrastructure.repository.mapper.TransactionJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository jpaRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return TransactionJpaMapper.toDomain(
                jpaRepository.save(TransactionJpaMapper.toEntity(transaction)));
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return jpaRepository.findById(id).map(TransactionJpaMapper::toDomain);
    }

    @Override
    public Page<Transaction> list(TransactionFilter filter, Pageable pageable) {
        return jpaRepository.findAll(TransactionSpecification.from(filter), pageable)
                .map(TransactionJpaMapper::toDomain);
    }
}
