package com.eduardo.transfer_service.infrastructure.messaging;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // Topics published by transfer-service
    public static final String TOPIC_TRANSFER_REQUESTED = "transfer.requested";
    public static final String TOPIC_CREDIT_ACCOUNT     = "credit.account";
    public static final String TOPIC_REFUND_ACCOUNT     = "refund.account";

    // Topics consumed by transfer-service
    public static final String TOPIC_ACCOUNT_DEBITED  = "account.debited";
    public static final String TOPIC_DEBIT_FAILED     = "debit.failed";
    public static final String TOPIC_ACCOUNT_CREDITED = "account.credited";
    public static final String TOPIC_CREDIT_FAILED    = "credit.failed";
    public static final String TOPIC_ACCOUNT_REFUNDED = "account.refunded";

    @Bean
    public NewTopic transferRequestedTopic() {
        return TopicBuilder.name(TOPIC_TRANSFER_REQUESTED).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic creditAccountTopic() {
        return TopicBuilder.name(TOPIC_CREDIT_ACCOUNT).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic refundAccountTopic() {
        return TopicBuilder.name(TOPIC_REFUND_ACCOUNT).partitions(3).replicas(1).build();
    }
}
