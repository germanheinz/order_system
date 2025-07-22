package com.order.system.data.order.adapter;

import com.order.system.application.service.ports.output.repository.OrderRepository;
import com.order.system.data.order.repository.OrderJpaRepository;
import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.valueobject.TrackingId;
import com.order.system.domain.valueobject.OrderId;
import com.order.system.data.order.mapper.OrderDataAccessMapper;

import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository,
                               OrderDataAccessMapper orderDataAccessMapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(orderJpaRepository
                .save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue()).map(orderDataAccessMapper::orderEntityToOrder);
    }



    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
