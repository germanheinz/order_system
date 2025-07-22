package com.order.system.payment.service.domain.ports.output.message.publisher;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.notification.service.domain.event.PaymentCancelledEvent;

public interface PaymentCancelledMessagePublisher extends DomainEventPublisher<PaymentCancelledEvent> {
}
