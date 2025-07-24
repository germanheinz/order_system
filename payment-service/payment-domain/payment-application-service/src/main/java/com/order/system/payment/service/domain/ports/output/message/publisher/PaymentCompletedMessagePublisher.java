package com.order.system.payment.service.domain.ports.output.message.publisher;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.stock.service.domain.event.PaymentCompletedEvent;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}