package com.eduardo.auth_service.infrastructure.repository.jpa;

import com.eduardo.auth_service.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "credentials")
@Getter
public class CredentialJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "user_id")
    private UUID userId;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;

    protected CredentialJpaEntity() {}

    public CredentialJpaEntity(
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
}
