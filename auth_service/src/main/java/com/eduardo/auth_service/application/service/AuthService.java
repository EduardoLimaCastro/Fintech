package com.eduardo.auth_service.application.service;

import com.eduardo.auth_service.application.dto.LoginRequest;
import com.eduardo.auth_service.application.dto.RefreshRequest;
import com.eduardo.auth_service.application.dto.TokenResponse;
import com.eduardo.auth_service.application.port.out.CredentialRepositoryPort;
import com.eduardo.auth_service.application.port.out.RefreshTokenPort;
import com.eduardo.auth_service.application.port.out.TokenPort;
import com.eduardo.auth_service.domain.exception.InvalidCredentialsException;
import com.eduardo.auth_service.domain.model.Credential;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final long ACCESS_TOKEN_EXPIRES_IN = 900L; // 15 min in seconds

    private final CredentialRepositoryPort credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenPort tokenPort;
    private final RefreshTokenPort refreshTokenPort;

    public TokenResponse login(LoginRequest request) {
        Credential credential = credentialRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), credential.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        if (!credential.isActive()) {
            throw new InvalidCredentialsException("Account is inactive");
        }

        String accessToken  = tokenPort.generateAccessToken(credential.getId(), credential.getEmail(), credential.getRole());
        String refreshToken = refreshTokenPort.create(credential.getId(), credential.getEmail(), credential.getRole());

        logger.info("Login successful for '{}'", credential.getEmail());
        return new TokenResponse(accessToken, "Bearer", ACCESS_TOKEN_EXPIRES_IN, refreshToken);
    }

    public TokenResponse refresh(RefreshRequest request) {
        RefreshTokenPort.TokenData data = refreshTokenPort.validate(request.refreshToken());

        // Rotate: revoke old, issue new
        refreshTokenPort.revoke(request.refreshToken());
        String newAccessToken  = tokenPort.generateAccessToken(data.credentialId(), data.email(), data.role());
        String newRefreshToken = refreshTokenPort.create(data.credentialId(), data.email(), data.role());

        logger.info("Token refreshed for '{}'", data.email());
        return new TokenResponse(newAccessToken, "Bearer", ACCESS_TOKEN_EXPIRES_IN, newRefreshToken);
    }

    public void logout(RefreshRequest request) {
        refreshTokenPort.revoke(request.refreshToken());
        logger.info("Logout: refresh token revoked");
    }
}
