package com.order.system.stock.service.messaging.mapper;


import com.order.system.domain.valueobject.ProductId;
import com.order.system.domain.valueobject.StockOrderStatus;
import com.order.system.kafka.order.avro.model.OrderApprovalStatus;
import com.order.system.kafka.order.avro.model.StockApprovalRequestAvroModel;
import com.order.system.kafka.order.avro.model.StockApprovalResponseAvroModel;
import com.order.system.stock.service.domain.dto.StockApprovalRequest;
import com.order.system.stock.service.domain.entity.Product;
import com.order.system.stock.service.domain.event.OrderApprovalEvent;
import com.order.system.stock.service.domain.event.OrderRejectedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StockMessagingDataMapper {
    public StockApprovalResponseAvroModel
    orderApprovedEventToStockApprovalResponseAvroModel(OrderApprovalEvent orderApprovedEvent) {
        return StockApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(orderApprovedEvent.getOrderApproval().getOrderId().getValue().toString())
                .setStockId(orderApprovedEvent.getStockId().getValue().toString())
                .setCreatedAt(orderApprovedEvent.getCreatedAt().toInstant())
                .setOrderApprovalStatus(OrderApprovalStatus.valueOf(orderApprovedEvent.
                        getOrderApproval().getApprovalStatus().name()))
                .setFailureMessages(orderApprovedEvent.getFailureMessages())
                .build();
    }

    public StockApprovalResponseAvroModel
    orderRejectedEventToStockApprovalResponseAvroModel(OrderRejectedEvent orderRejectedEvent) {
        return StockApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(orderRejectedEvent.getOrderApproval().getOrderId().getValue().toString())
                .setStockId(orderRejectedEvent.getStockId().getValue().toString())
                .setCreatedAt(orderRejectedEvent.getCreatedAt().toInstant())
                .setOrderApprovalStatus(OrderApprovalStatus.valueOf(orderRejectedEvent.
                        getOrderApproval().getApprovalStatus().name()))
                .setFailureMessages(orderRejectedEvent.getFailureMessages())
                .build();
    }

    //TODO CAMBIAR POR STOCK
    public StockApprovalRequest
    stockApprovalRequestAvroModelToStockApproval(StockApprovalRequestAvroModel
                                                                   stockApprovalRequestAvroModel) {
        return StockApprovalRequest.builder()
                .id(stockApprovalRequestAvroModel.getId())
                .sagaId(stockApprovalRequestAvroModel.getSagaId())
                .stockId(stockApprovalRequestAvroModel.getStockId())
                .orderId(stockApprovalRequestAvroModel.getOrderId())
                .stockOrderStatus(StockOrderStatus.valueOf(stockApprovalRequestAvroModel
                        .getStockOrderStatus().name()))
                .products(stockApprovalRequestAvroModel.getProducts()
                        .stream().map(avroModel ->
                                Product.builder()
                                        .productId(new ProductId(UUID.fromString(avroModel.getId())))
                                        .quantity(avroModel.getQuantity())
                                        .build())
                        .collect(Collectors.toList()))
                .price(stockApprovalRequestAvroModel.getPrice())
                .createdAt(stockApprovalRequestAvroModel.getCreatedAt())
                .build();
    }
}