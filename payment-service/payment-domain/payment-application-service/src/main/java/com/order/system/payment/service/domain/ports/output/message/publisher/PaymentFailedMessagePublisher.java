package com.order.system.payment.service.domain.ports.output.message.publisher;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.notification.service.domain.event.PaymentFailedEvent;

public interface PaymentFailedMessagePublisher extends DomainEventPublisher<PaymentFailedEvent> {
}
