package com.eduardo.auth_service.infrastructure.repository.mapper;

import com.eduardo.auth_service.domain.model.Credential;
import com.eduardo.auth_service.infrastructure.repository.jpa.CredentialJpaEntity;

public final class CredentialJpaMapper {

    private CredentialJpaMapper() {}

    public static CredentialJpaEntity toEntity(Credential credential) {
        return new CredentialJpaEntity(
                credential.getId(),
                credential.getEmail(),
                credential.getPasswordHash(),
                credential.getRole(),
                credential.getUserId(),
                credential.isActive(),
                credential.getCreatedAt(),
                credential.getVersion()
        );
    }

    public static Credential toDomain(CredentialJpaEntity entity) {
        return Credential.reconstitute(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.getUserId(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getVersion()
        );
    }
}
