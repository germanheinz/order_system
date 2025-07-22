//package com.order.system.service.messaging.listener.kafka;
//
//import com.order.system.application.service.ports.input.message.listener.payment.NotifiacionResponseMessageListener;
//import com.order.system.kafka.comsumer.KafkaConsumer;
//import com.order.system.kafka.order.avro.model.OrderApprovalStatus;
//import com.order.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
//import com.order.system.service.messaging.mapper.OrderMessagingDataMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import org.springframework.kafka.support.KafkaHeaders;
//
//
//import java.util.List;
//
//import static com.order.system.domain.core.entity.Order.FAILURE_MESSAGE_DELIMITER;
//
//
//@Slf4j
//@Component
//public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {
//
//    private final NotifiacionResponseMessageListener notifiacionResponseMessageListener;
//    private final OrderMessagingDataMapper orderMessagingDataMapper;
//
//    public RestaurantApprovalResponseKafkaListener(NotifiacionResponseMessageListener
//                                                           notifiacionResponseMessageListener,
//                                                   OrderMessagingDataMapper orderMessagingDataMapper) {
//        this.notifiacionResponseMessageListener = notifiacionResponseMessageListener;
//        this.orderMessagingDataMapper = orderMessagingDataMapper;
//    }
//
//    @Override
//    @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
//                topics = "${order-service.restaurant-approval-response-topic-name}")
//    public void receive(@Payload List<RestaurantApprovalResponseAvroModel> messages,
//                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
//                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
//        log.info("{} number of restaurant approval responses received with keys {}, partitions {} and offsets {}",
//                messages.size(),
//                keys.toString(),
//                partitions.toString(),
//                offsets.toString());
//
//        messages.forEach(restaurantApprovalResponseAvroModel -> {
//            if (OrderApprovalStatus.APPROVED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
//                log.info("Processing approved order for order id: {}",
//                        restaurantApprovalResponseAvroModel.getOrderId());
//                notifiacionResponseMessageListener.notifiactionSent(orderMessagingDataMapper
//                        .approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponseAvroModel));
//            } else if (OrderApprovalStatus.REJECTED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
//                log.info("Processing rejected order for order id: {}, with failure messages: {}",
//                        restaurantApprovalResponseAvroModel.getOrderId(),
//                        String.join(FAILURE_MESSAGE_DELIMITER,
//                                restaurantApprovalResponseAvroModel.getFailureMessages()));
//                notifiacionResponseMessageListener.notificationFailed(orderMessagingDataMapper
//                        .approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponseAvroModel));
//            }
//        });
//
//    }
//}
