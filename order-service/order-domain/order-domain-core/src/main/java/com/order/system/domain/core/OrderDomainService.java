package com.order.system.domain.core;

import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.entity.Stock;
import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Stock stock, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher);

    OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher);

    void cancelOrder(Order order, List<String> failureMessages);
}