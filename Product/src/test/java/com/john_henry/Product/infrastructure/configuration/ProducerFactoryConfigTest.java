package com.john_henry.Product.infrastructure.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducerFactoryConfigTest {

    @InjectMocks
    private ProducerFactoryConfig producerFactoryConfig;

    @Mock
    private KafkaProperties kafkaProperties;

    @Mock
    private ProducerFactory<String,Integer> producerFactory;

    @Test
    void producerFactory_ShouldCreateProducerFactoryWithCorrectConfigs() {
        List<String> bootstrapServers = List.of("localhost:9092");
        when(kafkaProperties.getBootstrapServers()).thenReturn(bootstrapServers);

        ProducerFactory<String, Integer> producerFactory = producerFactoryConfig.producerFactory();
        Map<String, Object> configs = ((DefaultKafkaProducerFactory<String, Integer>) producerFactory).getConfigurationProperties();

        assertNotNull(producerFactory);
        assertEquals(bootstrapServers, configs.get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(StringSerializer.class, configs.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
        assertEquals(IntegerSerializer.class, configs.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    }

    @Test
    void kafkaTemplate_ShouldCreateKafkaTemplateWithProducerFactory() {
        ProducerFactoryConfig spyConfig = spy(producerFactoryConfig);
        doReturn(producerFactory).when(spyConfig).producerFactory();

        KafkaTemplate<String, Integer> kafkaTemplate = spyConfig.kafkaTemplate();

        assertNotNull(kafkaTemplate);
        assertEquals(producerFactory, kafkaTemplate.getProducerFactory());
    }

}