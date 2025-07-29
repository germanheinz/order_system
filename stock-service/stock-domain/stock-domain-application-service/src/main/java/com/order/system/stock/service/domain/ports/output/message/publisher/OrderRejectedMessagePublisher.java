package com.order.system.stock.service.domain.ports.output.message.publisher;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.stock.service.domain.event.OrderRejectedEvent;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {

    void publish(OrderRejectedEvent orderApprovedEvent);
}
