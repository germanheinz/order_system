package com.order.system.application.service.ports.output.message.publisher.payment;


import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}