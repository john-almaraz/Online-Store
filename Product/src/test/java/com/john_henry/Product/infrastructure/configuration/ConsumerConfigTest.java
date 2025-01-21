package com.john_henry.Product.infrastructure.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsumerConfigTest {

    @InjectMocks
    private ConsumerConfig consumerConfig;

    @Mock
    private KafkaProperties kafkaProperties;

    @Mock
    private ConsumerFactory<String, Integer> consumerFactory;

    @Test
    void consumerFactory_ShouldCreateConsumerFactoryWithCorrectConfigs() {
        List<String> bootstrapServers = List.of("localhost:9092");

        when(kafkaProperties.getBootstrapServers()).thenReturn(bootstrapServers);

        ConsumerFactory<String, Integer> consumerFactory = consumerConfig.consumerFactory();
        Map<String, Object> configs = ((DefaultKafkaConsumerFactory<String, Integer>) consumerFactory).getConfigurationProperties();

        assertNotNull(consumerFactory);
        assertEquals(bootstrapServers, configs.get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(StringDeserializer.class, configs.get(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
        assertEquals(IntegerDeserializer.class, configs.get(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
    }

    @Test
    void containerFactory_ShouldCreateContainerFactoryWithConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Integer> containerFactory = consumerConfig.containerFactory(consumerFactory);

        assertNotNull(containerFactory);
        assertEquals(consumerFactory, containerFactory.getConsumerFactory());
    }
}