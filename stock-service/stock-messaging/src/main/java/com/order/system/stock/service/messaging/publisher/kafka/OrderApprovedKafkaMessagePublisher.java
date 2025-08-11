package com.order.system.stock.service.messaging.publisher.kafka;

import com.order.system.kafka.order.avro.model.StockApprovalResponseAvroModel;
import com.order.system.kafka.producer.KafkaMessageHelper;
import com.order.system.kafka.producer.service.KafkaProducer;
import com.order.system.stock.service.domain.config.StockServiceConfigData;
import com.order.system.stock.service.domain.event.OrderApprovedEvent;
import com.order.system.stock.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
import com.order.system.stock.service.messaging.mapper.StockMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderApprovedKafkaMessagePublisher implements OrderApprovedMessagePublisher {

    private final StockMessagingDataMapper stockMessagingDataMapper;
    private final KafkaProducer<String, StockApprovalResponseAvroModel> kafkaProducer;
    private final StockServiceConfigData stockServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public OrderApprovedKafkaMessagePublisher(StockMessagingDataMapper stockMessagingDataMapper,
                                              KafkaProducer<String, StockApprovalResponseAvroModel> kafkaProducer,
                                              StockServiceConfigData stockServiceConfigData,
                                              KafkaMessageHelper kafkaMessageHelper) {
        this.stockMessagingDataMapper = stockMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.stockServiceConfigData = stockServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(OrderApprovedEvent orderApprovedEvent) {
        String orderId = orderApprovedEvent.getOrderApproval().getOrderId().getValue().toString();

        log.info("Received OrderApprovedEvent for order id: {}", orderId);

        try {
            StockApprovalResponseAvroModel stockApprovalResponseAvroModel =
                    stockMessagingDataMapper
                            .orderApprovedEventToStockApprovalResponseAvroModel(orderApprovedEvent);

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
