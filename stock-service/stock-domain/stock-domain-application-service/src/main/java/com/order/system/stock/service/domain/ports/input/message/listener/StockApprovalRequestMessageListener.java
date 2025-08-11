package com.order.system.stock.service.domain.ports.input.message.listener;

import com.order.system.stock.service.domain.dto.StockApprovalRequest;

public interface StockApprovalRequestMessageListener {
    void approveOrder(StockApprovalRequest stockApprovalRequest);
}