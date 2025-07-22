package com.order.system.restaurant.service.domain;

import com.order.system.restaurant.service.domain.dto.RestaurantApprovalRequest;
import com.order.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.order.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.order.system.restaurant.service.domain.ports.input.message.listener.notification.NotifiacionResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentRequestMessageListenerImpl implements NotifiacionResponseMessageListener {

    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;

    public PaymentRequestMessageListenerImpl(RestaurantApprovalRequestHelper restaurantApprovalRequestHelper) {
        this.restaurantApprovalRequestHelper = restaurantApprovalRequestHelper;
    }


    @Override
    public void notifiactionSent(RestaurantApprovalRequest restaurantApprovalRequest) {
        OrderApprovalEvent paymentEvent = restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
        fireEvent(paymentEvent);
    }

    @Override
    public void notificationFailed(RestaurantApprovalRequest restaurantApprovalRequest) {
        OrderApprovalEvent paymentEvent = restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
        fireEvent(paymentEvent);
    }

    private void fireEvent(OrderApprovalEvent orderApprovalEvent) {
        log.info("Publishing OrderApproved event with Order Approval id: {} and order id: {}",
                orderApprovalEvent.getOrderApproval().getId().getValue(),
                orderApprovalEvent.getOrderApproval().getOrderId());
        orderApprovalEvent.fire();
    }
}
