package com.eduardo.auth_service.domain.model;

import com.eduardo.auth_service.domain.enums.Role;
import com.eduardo.auth_service.domain.exception.InvalidCredentialsException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Credential {

    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final Role role;
    private final UUID userId;
    private boolean active;
    private final LocalDateTime createdAt;
    private final Long version;

    private Credential(
            UUID id,
            String email,
            String passwordHash,
            Role role,
            UUID userId,
            boolean active,
            LocalDateTime createdAt,
            Long version
    ) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.userId = userId;
        this.active = active;
        this.createdAt = createdAt;
        this.version = version;
    }

    // =========================
    // Factory methods
    // =========================

    public static Credential create(
            String email,
            String passwordHash,
            Role role,
            UUID userId,
            Clock clock
    ) {
        validate(email, passwordHash, role);
        return new Credential(
                UUID.randomUUID(),
                email,
                passwordHash,
                role,
                userId,
                true,
                LocalDateTime.now(clock),
                null
        );
    }

    public static Credential reconstitute(
            UUID id,
            String email,
            String passwordHash,
            Role role,
            UUID userId,
            boolean active,
            LocalDateTime createdAt,
            Long version
    ) {
        return new Credential(id, email, passwordHash, role, userId, active, createdAt, version);
    }

    // =========================
    // Domain behavior
    // =========================

    public void deactivate() {
        if (!this.active) {
            throw new InvalidCredentialsException("Credential is already inactive");
        }
        this.active = false;
    }

    // =========================
    // Validation
    // =========================

    private static void validate(String email, String passwordHash, Role role) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash is required");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }

    // =========================
    // Identity
    // =========================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credential other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // =========================
    // Getters
    // =========================

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public UUID getUserId() { return userId; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getVersion() { return version; }
}
