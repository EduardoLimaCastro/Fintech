CREATE TABLE credentials (
    id            UUID          NOT NULL,
    email         VARCHAR(255)  NOT NULL,
    password_hash VARCHAR(255)  NOT NULL,
    role          VARCHAR(20)   NOT NULL,
    user_id       UUID,
    active        BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP     NOT NULL,
    version       BIGINT        NOT NULL DEFAULT 0,

    CONSTRAINT pk_credentials PRIMARY KEY (id),
    CONSTRAINT uq_credentials_email UNIQUE (email)
);
