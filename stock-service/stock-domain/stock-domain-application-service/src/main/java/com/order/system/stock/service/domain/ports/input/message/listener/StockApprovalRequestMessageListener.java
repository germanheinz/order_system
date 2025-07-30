package com.order.system.stock.service.domain.ports.input.message.listener;

import com.order.system.stock.service.domain.dto.RestaurantApprovalRequest;

public interface StockApprovalRequestMessageListener {
    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}