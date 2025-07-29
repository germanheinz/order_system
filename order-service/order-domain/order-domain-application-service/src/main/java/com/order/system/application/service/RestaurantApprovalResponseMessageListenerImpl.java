package com.order.system.application.service;

import com.order.system.application.service.dto.message.StockApprovalResponse;
import com.order.system.application.service.ports.input.message.listener.payment.StockResponseMessageListener;
import com.order.system.domain.core.event.OrderCancelledEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.order.system.domain.core.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements StockResponseMessageListener {

    private final OrderApprovalSaga orderApprovalSaga;

    public RestaurantApprovalResponseMessageListenerImpl(OrderApprovalSaga orderApprovalSaga) {
        this.orderApprovalSaga = orderApprovalSaga;
    }

    @Override
    public void orderApproved(StockApprovalResponse stockApprovalResponse) {
        orderApprovalSaga.process(stockApprovalResponse);
        log.info("Order is approved for order id: {}", stockApprovalResponse.getOrderId());
    }

    @Override
    public void orderRejected(StockApprovalResponse stockApprovalResponse) {
        OrderCancelledEvent domainEvent = orderApprovalSaga.rollback(stockApprovalResponse);
        log.info("Publishing order cancelled event for order id: {} with failure messages: {}",
                stockApprovalResponse.getOrderId(),
                String.join(FAILURE_MESSAGE_DELIMITER, stockApprovalResponse.getFailureMessages())
        );
        domainEvent.fire();
    }

}
