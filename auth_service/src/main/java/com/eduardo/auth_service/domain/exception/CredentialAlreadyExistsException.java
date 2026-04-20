package com.eduardo.auth_service.domain.exception;

public class CredentialAlreadyExistsException extends RuntimeException {
    public CredentialAlreadyExistsException(String email) {
        super("Credential already exists for email: " + email);
    }
}
