package com.order.system.application.service.ports.output.message.publisher.payment;


import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderPaidStockRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}