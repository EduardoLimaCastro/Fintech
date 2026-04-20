package com.eduardo.auth_service.application.port.out;

import com.eduardo.auth_service.domain.enums.Role;

import java.util.UUID;

public interface TokenPort {
    String generateAccessToken(UUID credentialId, String email, Role role);
}
