package com.order.system.kafka.producer.service.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.order.system.kafka.producer.exception.KafkaProducerException;
import com.order.system.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import java.io.Serializable;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void send(String topicName, K key, V message, CompletableFuture<SendResult<K, V>> callback) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {

            CompletableFuture<SendResult<K, V>> completableFuture = kafkaTemplate.send(topicName, key, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Error sending message to Kafka", ex);
                    } else {
                        callback.complete(result);
                    }
                });

        } catch (KafkaException e) {
            log.error("Error on Kafka producer with key: {}, message: {} and exception: {}", key, message, e.getMessage());
            throw new KafkaProducerException("Error on Kafka producer with key: " + key + " and message: " + message);
        }
    }


    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
