package com.order.system.application.service;

import com.order.system.application.service.dto.create.CreateOrderCommand;
import com.order.system.application.service.mapper.OrderDataMapper;
import com.order.system.application.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.order.system.application.service.ports.output.repository.CustomerRepository;
import com.order.system.application.service.ports.output.repository.OrderRepository;
import com.order.system.application.service.ports.output.repository.StockRepository;
import com.order.system.domain.core.OrderDomainService;
import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.entity.Stock;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.core.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final StockRepository stockRepository;

    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedEventDomainEventPublisher;

    public OrderCreateHelper(
                             OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             OrderCreatedPaymentRequestMessagePublisher orderCreatedEventDomainEventPublisher,
                             StockRepository stockRepository,
                             CustomerRepository customerRepository,
                             OrderDataMapper orderDataMapper
    ) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.orderCreatedEventDomainEventPublisher = orderCreatedEventDomainEventPublisher;
        this.customerRepository = customerRepository;
        this.stockRepository = stockRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
//        checkCustomer(createOrderCommand.getCustomerId());
        Stock stock = checkStock(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, stock, orderCreatedEventDomainEventPublisher);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private Stock checkStock(CreateOrderCommand createOrderCommand) {
        Stock stock = orderDataMapper.createOrderCommandToStock(createOrderCommand);
        Optional<Stock> optionalStock = stockRepository.findStockInformation(stock);
        if (optionalStock.isEmpty()) {
            log.warn("Could not find stock with stock id: {}", createOrderCommand.getStockId());
            throw new OrderDomainException("Could not find stock with stock id: " +
                    createOrderCommand.getStockId());
        }
        return optionalStock.get();
    }

//    private Stock checkRestaurant(CreateOrderCommand createOrderCommand) {
//        Stock stock = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
//        Optional<Stock> optionalRestaurant = stockRepository.findStockInformation(stock);
//
//        Stock stock1 = Stock.builder()
//                .stockId(new StockId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45")))
//                .products(Arrays.asList(
//                        new Product(new ProductId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48")), "Pizza", new Money(BigDecimal.valueOf(10.99))),
//                        new Product(new ProductId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48")), "Burger", new Money(BigDecimal.valueOf(8.49)))
//                ))
//                .active(true)
//                .build();
//
//        if (optionalRestaurant.isEmpty()) {
//            log.warn("Could not find stock with stock id: {}", createOrderCommand.getStockId());
//            throw new OrderDomainException("Could not find stock with stock id: " +
//                    createOrderCommand.getStockId());
//        }
//        return stock1;
//    }

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
