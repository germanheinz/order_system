package com.order.system.stock.service.domain.ports.input.message.listener;

import com.order.system.stock.service.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {
    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}