package com.order.system.domain.core;


import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.entity.Product;
import com.order.system.domain.core.entity.Stock;
import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.domain.core.exception.OrderDomainException;
import com.order.system.domain.event.publisher.DomainEventPublisher;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    public static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Stock stock, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher) {
        validateStock(stock);
        setOrderProductInformation(order, stock);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)),orderCreatedEventDomainEventPublisher);
    }

    @Override
    public OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderPaidEventDomainEventPublisher);
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id: {}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderCancelledEventDomainEventPublisher);
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
    }

    private void validateStock(Stock stock) {
        if (!stock.isActive()) {
            throw new OrderDomainException("Stock with id " + stock.getId().getValue() +
                    " is currently not active!");
        }
    }

    private void setOrderProductInformation(Order order, Stock stock) {
        order.getItems().forEach(orderItem -> stock.getProducts().forEach(stockProduct -> {
            Product currentProduct = orderItem.getProduct();
            if (currentProduct.equals(stockProduct)) {
                currentProduct.updateWithConfirmedNameAndPrice(stockProduct.getName(),
                        stockProduct.getPrice());
            }
        }));
    }
}
