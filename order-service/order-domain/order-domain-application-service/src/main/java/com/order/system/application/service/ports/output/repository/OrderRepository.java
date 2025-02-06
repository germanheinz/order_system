package com.order.system.application.service.ports.output.repository;


import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.valueobject.TrackingId;
import com.order.system.domain.valueobject.OrderId;

import java.util.Optional;


public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);

    Optional<Order> findById(OrderId orderId);
}
