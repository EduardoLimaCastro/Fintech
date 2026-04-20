package com.eduardo.auth_service.application.dto;

import com.eduardo.auth_service.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotNull Role role,
        UUID userId
) {}
