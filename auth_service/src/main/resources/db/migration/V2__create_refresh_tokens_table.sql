CREATE TABLE refresh_tokens (
    id            UUID          NOT NULL,
    token         VARCHAR(255)  NOT NULL,
    credential_id UUID          NOT NULL,
    email         VARCHAR(255)  NOT NULL,
    role          VARCHAR(20)   NOT NULL,
    expires_at    TIMESTAMP     NOT NULL,
    revoked       BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMP     NOT NULL,

    CONSTRAINT pk_refresh_tokens PRIMARY KEY (id),
    CONSTRAINT uq_refresh_tokens_token UNIQUE (token),
    CONSTRAINT fk_refresh_tokens_credential FOREIGN KEY (credential_id) REFERENCES credentials (id)
);
