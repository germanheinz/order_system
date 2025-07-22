
package com.order.system.domain.core.event;

import com.order.system.domain.core.entity.Order;
import com.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent{
    private final DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher;

    public OrderCancelledEvent(Order order,
                               ZonedDateTime createdAt,
                               DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher) {
        super(order, createdAt);
        this.orderCancelledEventDomainEventPublisher = orderCancelledEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderCancelledEventDomainEventPublisher.publish(this);
    }
}
