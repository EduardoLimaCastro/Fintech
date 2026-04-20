package com.eduardo.transfer_service.infrastructure.messaging;

import com.eduardo.transfer_service.application.dto.event.CreditAccountCommand;
import com.eduardo.transfer_service.application.dto.event.RefundAccountCommand;
import com.eduardo.transfer_service.application.dto.event.TransferRequestedEvent;
import com.eduardo.transfer_service.application.port.out.TransferEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.eduardo.transfer_service.infrastructure.messaging.KafkaTopicConfig.*;

@Component
@RequiredArgsConstructor
public class TransferEventProducer implements TransferEventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(TransferEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishTransferRequested(TransferRequestedEvent event) {
        log.info("Publishing transfer.requested for transferId={}", event.transferId());
        kafkaTemplate.send(TOPIC_TRANSFER_REQUESTED, event.transferId().toString(), event);
    }

    @Override
    public void publishCreditAccount(CreditAccountCommand command) {
        log.info("Publishing credit.account for transferId={}", command.transferId());
        kafkaTemplate.send(TOPIC_CREDIT_ACCOUNT, command.transferId().toString(), command);
    }

    @Override
    public void publishRefundAccount(RefundAccountCommand command) {
        log.warn("Publishing refund.account for transferId={}", command.transferId());
        kafkaTemplate.send(TOPIC_REFUND_ACCOUNT, command.transferId().toString(), command);
    }
}
