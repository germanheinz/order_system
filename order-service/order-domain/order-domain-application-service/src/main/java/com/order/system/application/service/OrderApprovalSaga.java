package com.order.system.application.service;


import com.order.system.application.service.dto.message.StockApprovalResponse;
import com.order.system.application.service.ports.output.message.publisher.payment.OrderCancelPaymentRequestMessagePublisher;
import com.order.system.config.kafka.SagaStep;
import com.order.system.domain.core.OrderDomainService;
import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.domain.event.EmptyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class OrderApprovalSaga implements SagaStep<StockApprovalResponse, EmptyEvent, OrderCancelledEvent> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderCancelPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;


    public OrderApprovalSaga(OrderDomainService orderDomainService, OrderSagaHelper orderSagaHelper, OrderCancelPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher) {
        this.orderDomainService = orderDomainService;
        this.orderSagaHelper = orderSagaHelper;
        this.orderCancelledPaymentRequestMessagePublisher = orderCancelledPaymentRequestMessagePublisher;
    }

    @Override
    @Transactional
    public EmptyEvent process(StockApprovalResponse stockApprovalResponse) {
        log.info("Approving order with id: {}", stockApprovalResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(stockApprovalResponse.getOrderId());
        orderDomainService.approveOrder(order);
        orderSagaHelper.saveOrder(order);
        log.info("Order with id: {} is approved", order.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public OrderCancelledEvent rollback(StockApprovalResponse stockApprovalResponse) {
        log.info("Cancelling order with id: {}", stockApprovalResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(stockApprovalResponse.getOrderId());
        OrderCancelledEvent domainEvent = orderDomainService.cancelOrderPayment(order,
                stockApprovalResponse.getFailureMessages(), orderCancelledPaymentRequestMessagePublisher);
        orderSagaHelper.saveOrder(order);
        log.info("Order with id: {} is cancelling", order.getId().getValue());
        return domainEvent;
    }
}
