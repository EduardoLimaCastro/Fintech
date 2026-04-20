package com.eduardo.transfer_service.application.dto.event;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDebitedEvent(
        UUID transferId,
        UUID sourceAccountId,
        BigDecimal amount
) {}
