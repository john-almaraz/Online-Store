package com.john_henry.Product.infrastructure.adapters.input.kafka;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Getter
@Slf4j
@Component
public class ListenerProduct {

    private final Map<Integer, CompletableFuture<Integer>> responseMap = new HashMap<>();

    public CompletableFuture<Integer> registerFuture(Integer sellerId) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        responseMap.put(sellerId, future);
        return future;
    }

    @KafkaListener(groupId = "group-1",
            topicPartitions = @TopicPartition(topic = "user-response-topic",partitions = {"0"})
            ,containerFactory = "containerFactory")
    public void listener(Integer response){
        Integer key = 4;

        log.info("Received response: {}", response);
        CompletableFuture<Integer> future = responseMap.remove(key);

        if (future != null) {
            future.complete(response);
        } else {
            log.error("No matching future for response: {} ", response);
        }
    }

}
