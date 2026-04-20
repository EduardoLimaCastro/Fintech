package com.eduardo.transaction_service.application.service;

import com.eduardo.transaction_service.application.dto.CreateTransactionRequest;
import com.eduardo.transaction_service.application.dto.TransactionResponse;
import com.eduardo.transaction_service.application.filter.TransactionFilter;
import com.eduardo.transaction_service.application.mapper.TransactionMapper;
import com.eduardo.transaction_service.application.port.out.TransactionRepositoryPort;
import com.eduardo.transaction_service.domain.exception.TransactionNotFoundException;
import com.eduardo.transaction_service.domain.model.Transaction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepositoryPort repository;
    private final Clock clock;

    public TransactionResponse create(CreateTransactionRequest request) {
        Transaction transaction = TransactionMapper.toDomain(request, clock);
        Transaction saved = repository.save(transaction);
        logger.info("Transaction created with id '{}' for account '{}'", saved.getId(), saved.getAccountId());
        return TransactionMapper.toResponse(saved);
    }

    public TransactionResponse findById(UUID id) {
        return repository.findById(id)
                .map(TransactionMapper::toResponse)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public Page<TransactionResponse> list(TransactionFilter filter, Pageable pageable) {
        return repository.list(filter, pageable).map(TransactionMapper::toResponse);
    }
}
