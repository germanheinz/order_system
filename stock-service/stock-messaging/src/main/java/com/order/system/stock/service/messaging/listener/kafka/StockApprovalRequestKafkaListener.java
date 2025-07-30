package com.order.system.stock.service.messaging.listener.kafka;

import com.order.system.kafka.comsumer.KafkaConsumer;
import com.order.system.kafka.order.avro.model.StockApprovalRequestAvroModel;
import com.order.system.stock.service.domain.ports.input.message.listener.StockApprovalRequestMessageListener;
import com.order.system.stock.service.messaging.mapper.StockMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StockApprovalRequestKafkaListener implements KafkaConsumer<StockApprovalRequestAvroModel> {

    private final StockApprovalRequestMessageListener stockApprovalRequestMessageListener;
    private final StockMessagingDataMapper stockMessagingDataMapper;

    public StockApprovalRequestKafkaListener(StockApprovalRequestMessageListener
                                                     stockApprovalRequestMessageListener,
                                             StockMessagingDataMapper
                                                     stockMessagingDataMapper) {
        this.stockApprovalRequestMessageListener = stockApprovalRequestMessageListener;
        this.stockMessagingDataMapper = stockMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.stock-approval-consumer-group-id}",
            topics = "${stock-service.stock-approval-request-topic-name}")
    public void receive(@Payload List<StockApprovalRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of orders approval requests received with keys {}, partitions {} and offsets {}" +
                        ", sending for stock approval",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(restaurantApprovalRequestAvroModel -> {
            log.info("Processing order approval for order id: {}", restaurantApprovalRequestAvroModel.getOrderId());
            stockApprovalRequestMessageListener.approveOrder(stockMessagingDataMapper.
                    restaurantApprovalRequestAvroModelToRestaurantApproval(restaurantApprovalRequestAvroModel));
        });
    }

}