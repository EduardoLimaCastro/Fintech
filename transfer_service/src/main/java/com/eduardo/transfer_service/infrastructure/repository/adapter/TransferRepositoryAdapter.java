package com.eduardo.transfer_service.infrastructure.repository.adapter;

import com.eduardo.transfer_service.application.port.out.TransferRepositoryPort;
import com.eduardo.transfer_service.domain.model.Transfer;
import com.eduardo.transfer_service.infrastructure.repository.jpa.TransferJpaRepository;
import com.eduardo.transfer_service.infrastructure.repository.mapper.TransferJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransferRepositoryAdapter implements TransferRepositoryPort {

    private final TransferJpaRepository jpaRepository;

    @Override
    public Transfer save(Transfer transfer) {
        return TransferJpaMapper.toDomain(
                jpaRepository.save(TransferJpaMapper.toEntity(transfer))
        );
    }

    @Override
    public Optional<Transfer> findById(UUID id) {
        return jpaRepository.findById(id).map(TransferJpaMapper::toDomain);
    }
}
