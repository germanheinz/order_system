package com.order.system.stock.service.messaging.publisher.kafka;

import com.order.system.application.service.config.OrderServiceConfigData;
import com.order.system.application.service.ports.output.message.publisher.payment.OrderPaidStockRequestMessagePublisher;
import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.kafka.order.avro.model.StockApprovalRequestAvroModel;
import com.order.system.kafka.producer.KafkaMessageHelper;
import com.order.system.kafka.producer.service.KafkaProducer;
import com.order.system.stock.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("payOrderKafkaMessagePublisher")
public class PayOrderKafkaMessagePublisher implements OrderPaidStockRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, StockApprovalRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    public PayOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                         OrderServiceConfigData orderServiceConfigData,
                                         KafkaProducer<String, StockApprovalRequestAvroModel> kafkaProducer,
                                         KafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();

        try {
            StockApprovalRequestAvroModel stockApprovalRequestAvroModel =
                    orderMessagingDataMapper.orderPaidEventToStockApprovalRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getStockApprovalRequestTopicName(),
                    orderId,
                    stockApprovalRequestAvroModel,
                    orderKafkaMessageHelper
                            .getKafkaCallback(orderServiceConfigData.getStockApprovalRequestTopicName(),
                                    stockApprovalRequestAvroModel,
                                    orderId,
                                    "StockApprovalRequestAvroModel"));

            log.info("StockApprovalRequestAvroModel sent to kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending StockApprovalRequestAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}
