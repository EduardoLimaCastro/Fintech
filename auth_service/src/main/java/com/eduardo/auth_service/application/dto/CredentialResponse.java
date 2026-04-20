package com.eduardo.auth_service.application.dto;

import com.eduardo.auth_service.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record CredentialResponse(
        UUID id,
        String email,
        Role role,
        UUID userId,
        boolean active,
        LocalDateTime createdAt
) {}
