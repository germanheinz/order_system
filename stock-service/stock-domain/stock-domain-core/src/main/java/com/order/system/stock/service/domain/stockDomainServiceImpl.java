package com.order.system.stock.service.domain;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.domain.valueobject.OrderApprovalStatus;
import com.order.system.stock.service.domain.entity.Stock;
import com.order.system.stock.service.domain.event.OrderApprovalEvent;
import com.order.system.stock.service.domain.event.OrderApprovedEvent;
import com.order.system.stock.service.domain.event.OrderRejectedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@Slf4j
public class stockDomainServiceImpl implements stockDomainService {

    @Override
    public OrderApprovalEvent validateOrder(Stock stock,
                                            List<String> failureMessages,
                                            DomainEventPublisher<OrderApprovedEvent>
                                                    orderApprovedEventDomainEventPublisher,
                                            DomainEventPublisher<OrderRejectedEvent>
                                                    orderRejectedEventDomainEventPublisher) {
        stock.validateOrder(failureMessages);
//        log.("Validating order with id: {}", stock.getOrderDetail().getId().getValue());

        if (failureMessages.isEmpty()) {
            log.info("Order is approved for order id: {}", stock.getOrderDetail().getId().getValue());
            stock.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(stock.getOrderApproval(),
                    stock.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of("UTC")),
                    orderApprovedEventDomainEventPublisher);
        } else {
            log.info("Order is rejected for order id: {}", stock.getOrderDetail().getId().getValue());
            stock.constructOrderApproval(OrderApprovalStatus.REJECTED);
            return new OrderRejectedEvent(stock.getOrderApproval(),
                    stock.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of("UTC")),
                    orderRejectedEventDomainEventPublisher);
        }
    }
}
