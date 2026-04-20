package com.eduardo.auth_service.application.mapper;

import com.eduardo.auth_service.application.dto.CredentialResponse;
import com.eduardo.auth_service.domain.model.Credential;

public final class CredentialMapper {

    private CredentialMapper() {}

    public static CredentialResponse toResponse(Credential credential) {
        return new CredentialResponse(
                credential.getId(),
                credential.getEmail(),
                credential.getRole(),
                credential.getUserId(),
                credential.isActive(),
                credential.getCreatedAt()
        );
    }
}
