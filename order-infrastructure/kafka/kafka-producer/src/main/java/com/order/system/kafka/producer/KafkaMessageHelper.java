package com.order.system.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaMessageHelper {

    public <T> CompletableFuture<SendResult<String, T>> getKafkaCallback(
            String responseTopicName, T avroModel, String orderId, String avroModelName) {

        CompletableFuture<SendResult<String, T>> future = new CompletableFuture<>();

        future.thenAccept(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
            log.info("Received successful response from Kafka for order id: {}" +
                            " Topic: {} Partition: {} Offset: {} Timestamp: {}",
                    orderId,
                    metadata.topic(),
                    metadata.partition(),
                    metadata.offset(),
                    metadata.timestamp());
        }).exceptionally(ex -> {
            log.error("Error while sending {} message {} to topic {}",
                    avroModelName, avroModel.toString(), responseTopicName, ex);
            return null;
        });

        return future;
    }

}
