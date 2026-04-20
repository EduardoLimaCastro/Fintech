package com.eduardo.auth_service.application.port.out;

import com.eduardo.auth_service.domain.enums.Role;

import java.util.UUID;

public interface RefreshTokenPort {

    record TokenData(UUID credentialId, String email, Role role) {}

    String create(UUID credentialId, String email, Role role);
    TokenData validate(String token);
    void revoke(String token);
}
