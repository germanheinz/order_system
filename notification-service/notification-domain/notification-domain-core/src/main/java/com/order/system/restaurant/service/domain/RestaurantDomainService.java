package com.order.system.restaurant.service.domain;

import com.order.system.restaurant.service.domain.entity.Restaurant;
import com.order.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.order.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.order.system.restaurant.service.domain.event.OrderRejectedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(Restaurant restaurant,
                                     List<String> failureMessages,
                                     DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
                                     DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);
}
