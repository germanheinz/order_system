package com.order.system.stock.service.domain.ports.output.message.publisher;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.stock.service.domain.event.OrderApprovedEvent;

public interface OrderApprovedMessagePublisher extends DomainEventPublisher<OrderApprovedEvent> {

    void publish(OrderApprovedEvent orderApprovedEvent);
}
