package com.eduardo.transfer_service.infrastructure.messaging;

import com.eduardo.transfer_service.application.dto.event.AccountCreditedEvent;
import com.eduardo.transfer_service.application.dto.event.AccountDebitedEvent;
import com.eduardo.transfer_service.application.dto.event.AccountRefundedEvent;
import com.eduardo.transfer_service.application.dto.event.CreditFailedEvent;
import com.eduardo.transfer_service.application.dto.event.DebitFailedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> factory(Class<T> targetType) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "transfer-service-group");

        JsonDeserializer<T> valueDeserializer = new JsonDeserializer<>(targetType);
        valueDeserializer.addTrustedPackages("*");
        valueDeserializer.setUseTypeHeaders(false);

        ConsumerFactory<String, T> cf = new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), valueDeserializer);

        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountDebitedEvent>
    accountDebitedFactory() {
        return factory(AccountDebitedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DebitFailedEvent>
    debitFailedFactory() {
        return factory(DebitFailedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountCreditedEvent>
    accountCreditedFactory() {
        return factory(AccountCreditedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreditFailedEvent>
    creditFailedFactory() {
        return factory(CreditFailedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountRefundedEvent>
    accountRefundedFactory() {
        return factory(AccountRefundedEvent.class);
    }
}
