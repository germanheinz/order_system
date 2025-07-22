package com.order.system.restaurant.service.domain.ports.output.message.publish.payment;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.order.system.restaurant.service.domain.event.OrderRejectedEvent;

public interface OrderNotificationFailedRequestMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {
}