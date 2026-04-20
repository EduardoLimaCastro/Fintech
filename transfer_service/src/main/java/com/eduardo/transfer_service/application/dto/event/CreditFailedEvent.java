package com.eduardo.transfer_service.application.dto.event;

import java.util.UUID;

public record CreditFailedEvent(
        UUID transferId,
        UUID targetAccountId,
        String reason
) {}
