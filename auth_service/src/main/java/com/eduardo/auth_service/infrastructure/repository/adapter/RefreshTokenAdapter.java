package com.eduardo.auth_service.infrastructure.repository.adapter;

import com.eduardo.auth_service.application.port.out.RefreshTokenPort;
import com.eduardo.auth_service.domain.enums.Role;
import com.eduardo.auth_service.domain.exception.InvalidCredentialsException;
import com.eduardo.auth_service.infrastructure.repository.jpa.RefreshTokenJpaEntity;
import com.eduardo.auth_service.infrastructure.repository.jpa.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefreshTokenAdapter implements RefreshTokenPort {

    private static final long REFRESH_TOKEN_DAYS = 7L;

    private final RefreshTokenJpaRepository jpaRepository;
    private final Clock clock;

    @Override
    public String create(UUID credentialId, String email, Role role) {
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(clock);
        RefreshTokenJpaEntity entity = new RefreshTokenJpaEntity(
                UUID.randomUUID(),
                token,
                credentialId,
                email,
                role,
                now.plusDays(REFRESH_TOKEN_DAYS),
                false,
                now
        );
        jpaRepository.save(entity);
        return token;
    }

    @Override
    public TokenData validate(String token) {
        RefreshTokenJpaEntity entity = jpaRepository.findByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException("Refresh token not found"));

        if (entity.isRevoked()) {
            throw new InvalidCredentialsException("Refresh token has been revoked");
        }
        if (entity.getExpiresAt().isBefore(LocalDateTime.now(clock))) {
            throw new InvalidCredentialsException("Refresh token has expired");
        }

        return new TokenData(entity.getCredentialId(), entity.getEmail(), entity.getRole());
    }

    @Override
    public void revoke(String token) {
        jpaRepository.findByToken(token).ifPresent(entity -> {
            entity.revoke();
            jpaRepository.save(entity);
        });
    }
}
