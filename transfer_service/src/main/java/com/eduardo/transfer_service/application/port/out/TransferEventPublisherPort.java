package com.eduardo.transfer_service.application.port.out;

import com.eduardo.transfer_service.application.dto.event.CreditAccountCommand;
import com.eduardo.transfer_service.application.dto.event.RefundAccountCommand;
import com.eduardo.transfer_service.application.dto.event.TransferRequestedEvent;

public interface TransferEventPublisherPort {
    void publishTransferRequested(TransferRequestedEvent event);
    void publishCreditAccount(CreditAccountCommand command);
    void publishRefundAccount(RefundAccountCommand command);
}
