package com.order.system.domain.core.event;

import com.order.system.domain.event.DomainEvent;
import com.order.system.domain.core.entity.Order;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;

    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void fire() {

    }
}