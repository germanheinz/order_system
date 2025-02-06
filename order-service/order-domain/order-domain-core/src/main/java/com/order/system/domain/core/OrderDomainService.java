package com.order.system.domain.core;

import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.entity.Restaurant;
import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.core.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
    void cancelOrder(Order order, List<String> failureMessages);
}
