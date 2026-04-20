CREATE TABLE transactions (
    id                  UUID            NOT NULL,
    type                VARCHAR(20)     NOT NULL,
    amount              NUMERIC(19, 4)  NOT NULL,
    account_id          UUID            NOT NULL,
    related_account_id  UUID,
    description         VARCHAR(255),
    status              VARCHAR(20)     NOT NULL,
    created_at          TIMESTAMP       NOT NULL,
    version             BIGINT          NOT NULL DEFAULT 0,

    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

CREATE INDEX idx_transactions_account_id  ON transactions (account_id);
CREATE INDEX idx_transactions_type        ON transactions (type);
CREATE INDEX idx_transactions_status      ON transactions (status);
CREATE INDEX idx_transactions_created_at  ON transactions (created_at DESC);
