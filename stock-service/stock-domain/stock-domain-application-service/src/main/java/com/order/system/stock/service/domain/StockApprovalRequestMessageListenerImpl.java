package com.order.system.stock.service.domain;

import com.order.system.stock.service.domain.dto.StockApprovalRequest;
import com.order.system.stock.service.domain.event.OrderApprovalEvent;
import com.order.system.stock.service.domain.ports.input.message.listener.StockApprovalRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockApprovalRequestMessageListenerImpl implements StockApprovalRequestMessageListener {

    private final StockApprovalRequestHelper stockApprovalRequestHelper;

    public StockApprovalRequestMessageListenerImpl(StockApprovalRequestHelper
                                                           stockApprovalRequestHelper) {
        this.stockApprovalRequestHelper = stockApprovalRequestHelper;
    }

    @Override
    public void approveOrder(StockApprovalRequest stockApprovalRequest) {
        OrderApprovalEvent orderApprovalEvent =
                stockApprovalRequestHelper.persistOrderApproval(stockApprovalRequest);
        orderApprovalEvent.fire();
    }
}
