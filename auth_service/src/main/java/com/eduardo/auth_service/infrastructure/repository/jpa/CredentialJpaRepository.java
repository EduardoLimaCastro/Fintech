package com.eduardo.auth_service.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialJpaRepository extends JpaRepository<CredentialJpaEntity, UUID> {
    Optional<CredentialJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
