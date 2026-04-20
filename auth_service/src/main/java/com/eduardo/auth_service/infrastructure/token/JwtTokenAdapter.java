package com.eduardo.auth_service.infrastructure.token;

import com.eduardo.auth_service.application.port.out.TokenPort;
import com.eduardo.auth_service.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenAdapter implements TokenPort {

    private static final long ACCESS_TOKEN_SECONDS = 900L; // 15 min
    private static final String ISSUER = "http://localhost:8015";

    private final JwtEncoder jwtEncoder;
    private final Clock clock;

    @Override
    public String generateAccessToken(UUID credentialId, String email, Role role) {
        Instant now = clock.instant();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .subject(credentialId.toString())
                .claim("email", email)
                .claim("role", role.name())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_TOKEN_SECONDS))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
