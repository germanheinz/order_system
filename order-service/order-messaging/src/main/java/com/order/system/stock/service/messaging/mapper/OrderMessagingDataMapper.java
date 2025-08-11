package com.order.system.stock.service.messaging.mapper;

import com.order.system.application.service.dto.message.PaymentResponse;
import com.order.system.application.service.dto.message.StockApprovalResponse;
import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.kafka.order.avro.model.*;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {
        Order order = orderCreatedEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }

    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderCancelledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();
    }

    public StockApprovalRequestAvroModel
    orderPaidEventToStockApprovalRequestAvroModel(OrderPaidEvent orderPaidEvent) {
        Order order = orderPaidEvent.getOrder();
        return StockApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(order.getId().getValue().toString())
                .setStockId(order.getStockId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setStockOrderStatus(com.order.system.kafka.order.avro.model.StockOrderStatus
                        .valueOf(order.getOrderStatus().name()))
                .setProducts(order.getItems().stream().map(orderItem ->
                        com.order.system.kafka.order.avro.model.Product.newBuilder()
                                .setId(orderItem.getProduct().getId().getValue().toString())
                                .setQuantity(orderItem.getQuantity())
                                .build()).collect(Collectors.toList()))
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderPaidEvent.getCreatedAt().toInstant())
                .setStockOrderStatus(StockOrderStatus.PAID)
                .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel
                                                                             paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .paymentId(paymentResponseAvroModel.getPaymentId())
                .customerId(paymentResponseAvroModel.getCustomerId())
                .orderId(paymentResponseAvroModel.getOrderId())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(com.order.system.domain.valueobject.PaymentStatus.valueOf(
                        paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public StockApprovalResponse
    approvalResponseAvroModelToApprovalResponse(StockApprovalResponseAvroModel
                                                        stockApprovalResponseAvroModel) {
        return StockApprovalResponse.builder()
                .id(stockApprovalResponseAvroModel.getId())
                .sagaId(stockApprovalResponseAvroModel.getSagaId())
                .stockId(stockApprovalResponseAvroModel.getStockId())
                .orderId(stockApprovalResponseAvroModel.getOrderId())
                .createdAt(stockApprovalResponseAvroModel.getCreatedAt())
                .orderApprovalStatus(com.order.system.domain.valueobject.OrderApprovalStatus.valueOf(
                        stockApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(stockApprovalResponseAvroModel.getFailureMessages())
                .build();
    }
}
