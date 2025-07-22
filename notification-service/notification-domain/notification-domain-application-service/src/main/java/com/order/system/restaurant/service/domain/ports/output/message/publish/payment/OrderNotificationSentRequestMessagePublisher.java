package com.order.system.restaurant.service.domain.ports.output.message.publish.payment;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.restaurant.service.domain.event.OrderApprovalEvent;

public interface OrderNotificationSentRequestMessagePublisher extends DomainEventPublisher<OrderApprovalEvent> {
}