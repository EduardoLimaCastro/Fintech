package com.eduardo.auth_service.infrastructure.repository.adapter;

import com.eduardo.auth_service.application.port.out.CredentialRepositoryPort;
import com.eduardo.auth_service.domain.model.Credential;
import com.eduardo.auth_service.infrastructure.repository.jpa.CredentialJpaRepository;
import com.eduardo.auth_service.infrastructure.repository.mapper.CredentialJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CredentialRepositoryAdapter implements CredentialRepositoryPort {

    private final CredentialJpaRepository jpaRepository;

    @Override
    public Credential save(Credential credential) {
        return CredentialJpaMapper.toDomain(jpaRepository.save(CredentialJpaMapper.toEntity(credential)));
    }

    @Override
    public Optional<Credential> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(CredentialJpaMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
