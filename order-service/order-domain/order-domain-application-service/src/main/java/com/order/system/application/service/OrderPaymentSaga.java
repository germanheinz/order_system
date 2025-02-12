package com.order.system.domain.service;

import com.order.system.domain.core.entity.Order;

import com.order.system.config.kafka.SagaStep;
import com.order.system.domain.core.OrderDomainService;
import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.domain.core.exception.OrderNotFoundException;
import com.order.system.domain.event.EmptyEvent;
import com.order.system.domain.service.dto.message.PaymentResponse;
import com.order.system.domain.service.ports.ouput.message.publisher.payment.OrderPaidRestaurantRequestMessagePublisher;
import com.order.system.domain.service.ports.ouput.repository.OrderRepository;
import com.order.system.domain.valueobject.OrderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderPaymentSaga implements SagaStep<PaymentResponse, com.order.system.domain.core.event.OrderPaidEvent, EmptyEvent> {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher;

    public OrderPaymentSaga(OrderDomainService orderDomainService,
                            OrderRepository orderRepository,
                            OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.orderPaidRestaurantRequestMessagePublisher = orderPaidRestaurantRequestMessagePublisher;
    }

    @Override
    @Transactional
    public OrderPaidEvent process(PaymentResponse paymentResponse) {
        log.info("Completing payment for order with id: {}", paymentResponse.getOrderId());
        Order order = findOrder(paymentResponse.getOrderId());
        OrderPaidEvent domainEvent = orderDomainService.payOrder(order, orderPaidRestaurantRequestMessagePublisher);
        orderRepository.save(order);
        log.info("Order with id: {} is paid", order.getId().getValue());
        return domainEvent;
    }

    @Override
    @Transactional
    public EmptyEvent rollback(PaymentResponse paymentResponse) {
        log.info("Cancelling order with id: {}", paymentResponse.getOrderId());
        Order order = findOrder(paymentResponse.getOrderId());
        orderDomainService.cancelOrder(order, paymentResponse.getFailureMessages());
        orderRepository.save(order);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    private Order findOrder(String orderId) {
        Optional<Order> orderResponse = orderRepository.findById(new OrderId(UUID.fromString(orderId)));
        if (orderResponse.isEmpty()) {
            log.error("Order with id: {} could not be found!", orderId);
            throw new OrderNotFoundException("Order with id " + orderId + " could not be found!");
        }
        return orderResponse.get();
    }
}
