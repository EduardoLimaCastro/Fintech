package com.eduardo.auth_service.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token")  String accessToken,
        @JsonProperty("token_type")    String tokenType,
        @JsonProperty("expires_in")    long expiresIn,
        @JsonProperty("refresh_token") String refreshToken
) {}
