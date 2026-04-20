package com.eduardo.auth_service.application.port.out;

import com.eduardo.auth_service.domain.model.Credential;

import java.util.Optional;

public interface CredentialRepositoryPort {
    Credential save(Credential credential);
    Optional<Credential> findByEmail(String email);
    boolean existsByEmail(String email);
}
