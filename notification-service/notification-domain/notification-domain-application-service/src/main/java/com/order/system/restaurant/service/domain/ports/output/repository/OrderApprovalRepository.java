package com.order.system.restaurant.service.domain.ports.output.repository;

import com.order.system.restaurant.service.domain.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}