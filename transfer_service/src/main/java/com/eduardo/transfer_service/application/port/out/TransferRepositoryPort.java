package com.eduardo.transfer_service.application.port.out;

import com.eduardo.transfer_service.domain.model.Transfer;

import java.util.Optional;
import java.util.UUID;

public interface TransferRepositoryPort {
    Transfer save(Transfer transfer);
    Optional<Transfer> findById(UUID id);
}
