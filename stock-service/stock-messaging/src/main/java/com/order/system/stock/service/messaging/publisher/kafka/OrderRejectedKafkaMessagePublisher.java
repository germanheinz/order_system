package com.order.system.stock.service.messaging.publisher.kafka;

import com.order.system.kafka.order.avro.model.StockApprovalResponseAvroModel;
import com.order.system.kafka.producer.KafkaMessageHelper;
import com.order.system.kafka.producer.service.KafkaProducer;
import com.order.system.stock.service.domain.config.StockServiceConfigData;
import com.order.system.stock.service.domain.event.OrderRejectedEvent;
import com.order.system.stock.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import com.order.system.stock.service.messaging.mapper.StockMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderRejectedKafkaMessagePublisher implements OrderRejectedMessagePublisher {

    private final StockMessagingDataMapper stockMessagingDataMapper;
    private final KafkaProducer<String, StockApprovalResponseAvroModel> kafkaProducer;
    private final StockServiceConfigData stockServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public OrderRejectedKafkaMessagePublisher(StockMessagingDataMapper stockMessagingDataMapper,
                                              KafkaProducer<String, StockApprovalResponseAvroModel> kafkaProducer,
                                              StockServiceConfigData stockServiceConfigData,
                                              KafkaMessageHelper kafkaMessageHelper) {
        this.stockMessagingDataMapper = stockMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.stockServiceConfigData = stockServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(OrderRejectedEvent orderRejectedEvent) {
        String orderId = orderRejectedEvent.getOrderApproval().getOrderId().getValue().toString();

        log.info("Received OrderRejectedEvent for order id: {}", orderId);

        try {
            StockApprovalResponseAvroModel stockApprovalResponseAvroModel =
                    stockMessagingDataMapper
                            .orderRejectedEventToStockApprovalResponseAvroModel(orderRejectedEvent);

            kafkaProducer.send(stockServiceConfigData.getStockApprovalResponseTopicName(),
                    orderId,
                    stockApprovalResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(stockServiceConfigData
                                    .getStockApprovalResponseTopicName(),
                            stockApprovalResponseAvroModel,
                            orderId,
                            "StockApprovalResponseAvroModel"));

            log.info("StockApprovalResponseAvroModel sent to kafka at: {}", System.nanoTime());
        } catch (Exception e) {
            log.error("Error while sending StockApprovalResponseAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }

}
