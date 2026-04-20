package com.eduardo.auth_service.application.service;

import com.eduardo.auth_service.application.dto.CredentialResponse;
import com.eduardo.auth_service.application.dto.RegisterRequest;
import com.eduardo.auth_service.application.mapper.CredentialMapper;
import com.eduardo.auth_service.application.port.out.CredentialRepositoryPort;
import com.eduardo.auth_service.domain.exception.CredentialAlreadyExistsException;
import com.eduardo.auth_service.domain.model.Credential;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@Transactional
@RequiredArgsConstructor
public class CredentialService {

    private static final Logger logger = LoggerFactory.getLogger(CredentialService.class);

    private final CredentialRepositoryPort repository;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;

    public CredentialResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new CredentialAlreadyExistsException(request.email());
        }

        String hash = passwordEncoder.encode(request.password());
        Credential credential = Credential.create(request.email(), hash, request.role(), request.userId(), clock);
        Credential saved = repository.save(credential);

        logger.info("Credential created for email '{}'", saved.getEmail());
        return CredentialMapper.toResponse(saved);
    }
}
