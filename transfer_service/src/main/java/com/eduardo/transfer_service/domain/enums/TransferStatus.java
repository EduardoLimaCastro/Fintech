package com.eduardo.transfer_service.domain.enums;

public enum TransferStatus {

    PENDING {
        @Override
        public boolean canTransitionTo(TransferStatus target) {
            return target == DEBITED || target == FAILED;
        }
    },
    DEBITED {
        @Override
        public boolean canTransitionTo(TransferStatus target) {
            return target == COMPLETED || target == COMPENSATING;
        }
    },
    COMPENSATING {
        @Override
        public boolean canTransitionTo(TransferStatus target) {
            return target == FAILED;
        }
    },
    COMPLETED {
        @Override
        public boolean canTransitionTo(TransferStatus target) {
            return false; // terminal
        }
    },
    FAILED {
        @Override
        public boolean canTransitionTo(TransferStatus target) {
            return false; // terminal
        }
    };

    public abstract boolean canTransitionTo(TransferStatus target);
}
