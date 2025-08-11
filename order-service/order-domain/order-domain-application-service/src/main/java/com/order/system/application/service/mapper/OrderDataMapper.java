package com.order.system.application.service.mapper;


import com.order.system.application.service.dto.create.CreateOrderCommand;
import com.order.system.application.service.dto.create.CreateOrderResponse;
import com.order.system.application.service.dto.create.OrderAddress;
import com.order.system.application.service.dto.track.TrackOrderResponse;
import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.entity.OrderItem;
import com.order.system.domain.core.entity.Product;
import com.order.system.domain.core.entity.Stock;
import com.order.system.domain.core.valueobject.StreetAddress;
import com.order.system.domain.valueobject.CustomerId;
import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.ProductId;
import com.order.system.domain.valueobject.StockId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Stock createOrderCommandToStock(CreateOrderCommand createOrderCommand) {
        return Stock.builder()
                .stockId(new StockId(createOrderCommand.getStockId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                        new Product(new ProductId(orderItem.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .stockId(new StockId(createOrderCommand.getStockId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<com.order.system.application.service.dto.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem ->
                        OrderItem.builder()
                                .product(new Product(new ProductId(orderItem.getProductId())))
                                .price(new Money(orderItem.getPrice()))
                                .quantity(orderItem.getQuantity())
                                .subTotal(new Money(orderItem.getSubTotal()))
                                .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}
