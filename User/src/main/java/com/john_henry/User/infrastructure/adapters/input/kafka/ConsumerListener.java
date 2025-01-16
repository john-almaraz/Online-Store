package com.john_henry.User.infrastructure.adapters.input.kafka;

import com.john_henry.User.application.ports.input.SellerService;
import com.john_henry.User.domain.exception.SellerNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;

@Log4j2
@Configuration
public class ConsumerListener {

    private final SellerService sellerService;
    private final KafkaTemplate<String,Integer> kafkaTemplate;

    public ConsumerListener(SellerService sellerService, KafkaTemplate<String, Integer> kafkaTemplate) {
        this.sellerService = sellerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(groupId = "group-1",
            topicPartitions = @TopicPartition(topic = "product-request-topic",partitions = {"0"})
            ,containerFactory = "containerFactory")
    public void listener(Integer message){
        if (message == null) {
            log.warn("Received null message, ignoring.");
            return;
        }

        log.info("Received message: {}", message);

        int response = -1;

        try{
            response = sellerService.getSellerById(message) != null ? 1 : -1;
        }catch (SellerNotFoundException ex){
            log.error(ex.getMessage());
        }

        kafkaTemplate.send("user-response-topic", response);

        log.info("Sent response: {} ", response);
    }

}
