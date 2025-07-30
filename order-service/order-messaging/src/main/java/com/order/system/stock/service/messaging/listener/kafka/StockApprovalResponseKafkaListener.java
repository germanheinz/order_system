package com.order.system.stock.service.messaging.listener.kafka;

import com.order.system.application.service.ports.input.message.listener.payment.StockResponseMessageListener;
import com.order.system.kafka.comsumer.KafkaConsumer;
import com.order.system.kafka.order.avro.model.OrderApprovalStatus;
import com.order.system.kafka.order.avro.model.StockApprovalResponseAvroModel;
import com.order.system.stock.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.KafkaHeaders;


import java.util.List;

import static com.order.system.domain.core.entity.Order.FAILURE_MESSAGE_DELIMITER;


@Slf4j
@Component
public class StockApprovalResponseKafkaListener implements KafkaConsumer<StockApprovalResponseAvroModel> {

    private final StockResponseMessageListener stockResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public StockApprovalResponseKafkaListener(StockResponseMessageListener
                                                           stockResponseMessageListener,
                                              OrderMessagingDataMapper orderMessagingDataMapper) {
        this.stockResponseMessageListener = stockResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.stock-approval-consumer-group-id}",
                topics = "${order-service.stock-approval-response-topic-name}")
    public void receive(@Payload List<StockApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of stock approval responses received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(restaurantApprovalResponseAvroModel -> {
            if (OrderApprovalStatus.APPROVED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing approved order for order id: {}",
                        restaurantApprovalResponseAvroModel.getOrderId());
                stockResponseMessageListener.orderApproved(orderMessagingDataMapper
                        .approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponseAvroModel));
            } else if (OrderApprovalStatus.REJECTED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing rejected order for order id: {}, with failure messages: {}",
                        restaurantApprovalResponseAvroModel.getOrderId(),
                        String.join(FAILURE_MESSAGE_DELIMITER,
                                restaurantApprovalResponseAvroModel.getFailureMessages()));
                stockResponseMessageListener.orderRejected(orderMessagingDataMapper
                        .approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponseAvroModel));
            }
        });

    }
}
