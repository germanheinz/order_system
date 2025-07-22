package com.order.system.service.messaging.publisher.kafka;

import com.order.system.application.service.OrderCreateHelper;
import com.order.system.application.service.config.OrderServiceConfigData;
import com.order.system.application.service.ports.output.message.publisher.payment.OrderCancelPaymentRequestMessagePublisher;
import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.order.system.kafka.producer.KafkaMessageHelper;
import com.order.system.kafka.producer.service.KafkaProducer;
import com.order.system.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelOrderKafkaMessagePublisher implements OrderCancelPaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    public CancelOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                            OrderServiceConfigData orderServiceConfigData,
                                            KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer,
                                            KafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderCancelledEvent domainEvent) {

        String orderId = domainEvent.getOrder().getId().toString();
        log.info("Receiving orderCancelEvent for order id: {}", orderId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCancelledEventToPaymentRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(
                            orderServiceConfigData.getPaymentRequestTopicName(),
                            paymentRequestAvroModel, orderId, "paymentRequestAvroModel"));

            log.info("PaymentRequestAvroModel sent to Kafka for Order id: {}", paymentRequestAvroModel.getOrderId());
        }catch (Exception e){
            log.info("e", e);
        }
    }

}
