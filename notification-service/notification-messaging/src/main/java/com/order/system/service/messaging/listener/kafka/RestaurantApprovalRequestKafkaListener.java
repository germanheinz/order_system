package com.order.system.service.messaging.listener.kafka;



import com.order.system.kafka.comsumer.KafkaConsumer;
import com.order.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.order.system.restaurant.service.domain.ports.input.message.listener.notification.NotifiacionResponseMessageListener;
import com.order.system.service.messaging.mapper.RestaurantMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RestaurantApprovalRequestKafkaListener implements KafkaConsumer<RestaurantApprovalRequestAvroModel> {

    private final NotifiacionResponseMessageListener notifiacionResponseMessageListener;
    private final RestaurantMessagingDataMapper restaurantMessagingDataMapper;

    public RestaurantApprovalRequestKafkaListener(NotifiacionResponseMessageListener
                                                          notifiacionResponseMessageListener,
                                                  RestaurantMessagingDataMapper
                                                          restaurantMessagingDataMapper) {
        this.notifiacionResponseMessageListener = notifiacionResponseMessageListener;
        this.restaurantMessagingDataMapper = restaurantMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.notification-consumer-group-id}",
            topics = "${restaurant-service.notification-request-topic-name}")
    public void receive(@Payload List<RestaurantApprovalRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of orders approval requests received with keys {}, partitions {} and offsets {}" +
                        ", sending for restaurant approval",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(restaurantApprovalRequestAvroModel -> {
            log.info("Processing order approval for order id: {}", restaurantApprovalRequestAvroModel.getOrderId());
            notifiacionResponseMessageListener.notifiactionSent(restaurantMessagingDataMapper.
                    restaurantApprovalRequestAvroModelToRestaurantApproval(restaurantApprovalRequestAvroModel));
        });
    }

}