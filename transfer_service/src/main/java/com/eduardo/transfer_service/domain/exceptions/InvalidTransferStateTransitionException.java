package com.eduardo.transfer_service.domain.exceptions;

import com.eduardo.transfer_service.domain.enums.TransferStatus;

import java.util.UUID;

public class InvalidTransferStateTransitionException extends RuntimeException {
    public InvalidTransferStateTransitionException(UUID transferId,
                                                    TransferStatus from,
                                                    TransferStatus to) {
        super("Invalid transfer state transition for id " + transferId
                + ": " + from + " -> " + to);
    }
}
