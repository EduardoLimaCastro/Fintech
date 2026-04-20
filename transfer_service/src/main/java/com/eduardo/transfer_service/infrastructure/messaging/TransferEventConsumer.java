package com.eduardo.transfer_service.infrastructure.messaging;

import com.eduardo.transfer_service.application.dto.event.AccountCreditedEvent;
import com.eduardo.transfer_service.application.dto.event.AccountDebitedEvent;
import com.eduardo.transfer_service.application.dto.event.AccountRefundedEvent;
import com.eduardo.transfer_service.application.dto.event.CreditFailedEvent;
import com.eduardo.transfer_service.application.dto.event.DebitFailedEvent;
import com.eduardo.transfer_service.application.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static com.eduardo.transfer_service.infrastructure.messaging.KafkaTopicConfig.*;

@Component
@RequiredArgsConstructor
public class TransferEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TransferEventConsumer.class);

    private final TransferService transferService;

    @KafkaListener(topics = TOPIC_ACCOUNT_DEBITED, groupId = "transfer-service-group",
            containerFactory = "accountDebitedFactory")
    public void onAccountDebited(ConsumerRecord<String, AccountDebitedEvent> record,
                                  Acknowledgment ack) {
        log.info("Received account.debited for transferId={}", record.key());
        transferService.onAccountDebited(record.value());
        ack.acknowledge();
    }

    @KafkaListener(topics = TOPIC_DEBIT_FAILED, groupId = "transfer-service-group",
            containerFactory = "debitFailedFactory")
    public void onDebitFailed(ConsumerRecord<String, DebitFailedEvent> record,
                               Acknowledgment ack) {
        log.warn("Received debit.failed for transferId={}", record.key());
        transferService.onDebitFailed(record.value());
        ack.acknowledge();
    }

    @KafkaListener(topics = TOPIC_ACCOUNT_CREDITED, groupId = "transfer-service-group",
            containerFactory = "accountCreditedFactory")
    public void onAccountCredited(ConsumerRecord<String, AccountCreditedEvent> record,
                                   Acknowledgment ack) {
        log.info("Received account.credited for transferId={}", record.key());
        transferService.onAccountCredited(record.value());
        ack.acknowledge();
    }

    @KafkaListener(topics = TOPIC_CREDIT_FAILED, groupId = "transfer-service-group",
            containerFactory = "creditFailedFactory")
    public void onCreditFailed(ConsumerRecord<String, CreditFailedEvent> record,
                                Acknowledgment ack) {
        log.warn("Received credit.failed for transferId={}", record.key());
        transferService.onCreditFailed(record.value());
        ack.acknowledge();
    }

    @KafkaListener(topics = TOPIC_ACCOUNT_REFUNDED, groupId = "transfer-service-group",
            containerFactory = "accountRefundedFactory")
    public void onAccountRefunded(ConsumerRecord<String, AccountRefundedEvent> record,
                                   Acknowledgment ack) {
        log.info("Received account.refunded for transferId={}", record.key());
        transferService.onAccountRefunded(record.value());
        ack.acknowledge();
    }
}
