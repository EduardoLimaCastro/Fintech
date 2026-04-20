package com.eduardo.auth_service.infrastructure.repository.jpa;

import com.eduardo.auth_service.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Getter
public class RefreshTokenJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "credential_id", nullable = false)
    private UUID credentialId;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected RefreshTokenJpaEntity() {}

    public RefreshTokenJpaEntity(
            UUID id,
            String token,
            UUID credentialId,
            String email,
            Role role,
            LocalDateTime expiresAt,
            boolean revoked,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.token = token;
        this.credentialId = credentialId;
        this.email = email;
        this.role = role;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.createdAt = createdAt;
    }

    public void revoke() {
        this.revoked = true;
    }
}
