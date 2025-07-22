package com.order.system.restaurant.service.domain.ports.input.message.listener.notification;


import com.order.system.restaurant.service.domain.dto.RestaurantApprovalRequest;

public interface NotifiacionResponseMessageListener {

    void notifiactionSent(RestaurantApprovalRequest restaurantApprovalRequest);

    void notificationFailed(RestaurantApprovalRequest restaurantApprovalRequest);
}
