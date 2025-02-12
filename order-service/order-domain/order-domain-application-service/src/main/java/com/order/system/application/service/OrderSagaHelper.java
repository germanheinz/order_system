package com.order.system.domain.service;

import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.exception.OrderNotFoundException;
import com.order.system.domain.service.ports.ouput.repository.OrderRepository;
import com.order.system.domain.valueobject.OrderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderSagaHelper {

    private final OrderRepository orderRepository;

    public OrderSagaHelper(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    Order findOrder(String orderId) {
        Optional<Order> orderResponse = orderRepository.findById(new OrderId(UUID.fromString(orderId)));
        if (orderResponse.isEmpty()) {
            log.error("Order with id: {} could not be found!", orderId);
            throw new OrderNotFoundException("Order with id " + orderId + " could not be found!");
        }
        return orderResponse.get();
    }

    void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
