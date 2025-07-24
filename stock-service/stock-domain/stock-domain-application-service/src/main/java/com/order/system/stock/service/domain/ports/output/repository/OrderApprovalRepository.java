package com.order.system.stock.service.domain.ports.output.repository;

import com.order.system.stock.service.domain.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}
