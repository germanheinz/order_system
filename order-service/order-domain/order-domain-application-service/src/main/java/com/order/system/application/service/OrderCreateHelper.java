package com.order.system.application.service;

import com.order.system.application.service.dto.create.CreateOrderCommand;
import com.order.system.application.service.mapper.OrderDataMapper;
import com.order.system.application.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.order.system.application.service.ports.output.repository.OrderRepository;
import com.order.system.domain.core.OrderDomainService;
import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.entity.Product;
import com.order.system.domain.core.entity.Restaurant;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.core.exception.OrderDomainException;
import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.ProductId;
import com.order.system.domain.valueobject.RestaurantId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

//    private final CustomerRepository customerRepository;

//    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedEventDomainEventPublisher;

    public OrderCreateHelper(
                             OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             OrderCreatedPaymentRequestMessagePublisher orderCreatedEventDomainEventPublisher,
//                             RestaurantRepository restaurantRepository,
//                             CustomerRepository customerRepository,
                             OrderDataMapper orderDataMapper
    ) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.orderCreatedEventDomainEventPublisher = orderCreatedEventDomainEventPublisher;
//        this.customerRepository = customerRepository;
//        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
//        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant, orderCreatedEventDomainEventPublisher);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
//        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);

        Restaurant restaurant1 = Restaurant.builder()
                .restaurantId(new RestaurantId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45")))
                .products(Arrays.asList(
                        new Product(new ProductId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48")), "Pizza", new Money(BigDecimal.valueOf(10.99))),
                        new Product(new ProductId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48")), "Burger", new Money(BigDecimal.valueOf(8.49)))
                ))
                .active(true)
                .build();

//        if (optionalRestaurant.isEmpty()) {
//            log.warn("Could not find restaurant with restaurant id: {}", createOrderCommand.getRestaurantId());
//            throw new OrderDomainException("Could not find restaurant with restaurant id: " +
//                    createOrderCommand.getRestaurantId());
//        }
        return restaurant1;
    }

//    private void checkCustomer(UUID customerId) {
//        Optional<Customer> customer = customerRepository.findCustomer(customerId);
//        if (customer.isEmpty()) {
//            log.warn("Could not find customer with customer id: {}", customerId);
//            throw new OrderDomainException("Could not find customer with customer id: " + customer);
//        }
//    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}
