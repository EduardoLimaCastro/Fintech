CREATE TABLE transfers (
    id                  UUID            NOT NULL,
    source_account_id   UUID            NOT NULL,
    target_account_id   UUID            NOT NULL,
    amount              NUMERIC(19, 4)  NOT NULL,
    status              VARCHAR(20)     NOT NULL,
    failure_reason      TEXT,
    created_at          TIMESTAMP       NOT NULL,
    updated_at          TIMESTAMP       NOT NULL,

    CONSTRAINT pk_transfers PRIMARY KEY (id)
);

CREATE INDEX idx_transfers_source_account ON transfers (source_account_id);
CREATE INDEX idx_transfers_target_account ON transfers (target_account_id);
CREATE INDEX idx_transfers_status         ON transfers (status);
