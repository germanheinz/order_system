package com.order.system.stock.service.domain.mapper;

import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.OrderId;
import com.order.system.domain.valueobject.OrderStatus;
import com.order.system.domain.valueobject.StockId;
import com.order.system.stock.service.domain.dto.RestaurantApprovalRequest;
import com.order.system.stock.service.domain.entity.OrderDetail;
import com.order.system.stock.service.domain.entity.Product;
import com.order.system.stock.service.domain.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataMapper {
    public Stock restaurantApprovalRequestToRestaurant(RestaurantApprovalRequest
                                                                    restaurantApprovalRequest) {
        return Stock.builder()
                .stockId(new StockId(UUID.fromString(restaurantApprovalRequest.getStockId())))
                .orderDetail(OrderDetail.builder()
                        .orderId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())))
                        .products(restaurantApprovalRequest.getProducts().stream().map(
                                        product -> Product.builder()
                                                .productId(product.getId())
                                                .quantity(product.getQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                        .totalAmount(new Money(restaurantApprovalRequest.getPrice()))
                        .orderStatus(OrderStatus.valueOf(restaurantApprovalRequest.getRestaurantOrderStatus().name()))
                        .build())
                .build();
    }
}