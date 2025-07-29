package com.order.system.application.service.ports.input.message.listener.payment;

import com.order.system.application.service.dto.message.PaymentResponse;
import com.order.system.application.service.dto.message.StockApprovalResponse;

public interface StockResponseMessageListener {

    void orderApproved(StockApprovalResponse stockAprovalResponse);

    void orderRejected(StockApprovalResponse stockAprovalResponse);
}
