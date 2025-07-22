package com.order.system.restaurant.service.domain.dto;

import com.order.system.domain.valueobject.RestaurantOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.order.system.restaurant.service.domain.entity.Product;


@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalRequest {
    private String id;
    private String sagaId;
    private String restaurantId;
    private String orderId;
    private RestaurantOrderStatus restaurantOrderStatus;
    private java.util.List<Product> products;
    private java.math.BigDecimal price;
    private java.time.Instant createdAt;
}
