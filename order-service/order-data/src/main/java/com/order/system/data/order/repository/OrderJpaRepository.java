package com.order.system.data.order.repository;

import com.order.system.data.order.entity.OrderEntity;
import com.order.system.domain.core.entity.Order;
import com.order.system.domain.valueobject.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderEntity> findByTrackingId(UUID trackingId);

}
