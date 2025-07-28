package com.order.system.stock.service.domain;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.stock.service.domain.entity.Stock;
import com.order.system.stock.service.domain.event.OrderApprovalEvent;
import com.order.system.stock.service.domain.event.OrderApprovedEvent;
import com.order.system.stock.service.domain.event.OrderRejectedEvent;

import java.util.List;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(Stock restaurant,
                                     List<String> failureMessages,
                                     DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
                                     DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);
}
