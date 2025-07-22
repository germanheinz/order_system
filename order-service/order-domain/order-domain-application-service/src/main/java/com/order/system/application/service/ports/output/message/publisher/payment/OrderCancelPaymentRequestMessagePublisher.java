package com.order.system.application.service.ports.output.message.publisher.payment;


import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCancelPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}