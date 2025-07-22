package com.order.system.service.messaging.publisher.kafka;

import com.order.system.application.service.config.OrderServiceConfigData;
import com.order.system.application.service.ports.output.message.publisher.payment.OrderPaidRestaurantRequestMessagePublisher;
import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.order.system.kafka.producer.KafkaMessageHelper;
import com.order.system.kafka.producer.service.KafkaProducer;
import com.order.system.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateNotificationOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    public CreateNotificationOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                                        OrderServiceConfigData orderServiceConfigData,
                                                        KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer,
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
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel =
                    orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId,
                    restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper
                            .getKafkaCallback(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                                    restaurantApprovalRequestAvroModel,
                                    orderId,
                                    "RestaurantApprovalRequestAvroModel"));

            log.info("RestaurantApprovalRequestAvroModel sent to kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending RestaurantApprovalRequestAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}
