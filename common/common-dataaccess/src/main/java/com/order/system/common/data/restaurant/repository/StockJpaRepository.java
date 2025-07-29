package com.order.system.common.data.stock.repository;

import com.order.system.common.data.stock.entity.StockEntity;
import com.order.system.common.data.stock.entity.StockEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, StockEntityId> {

    Optional<List<StockEntity>> findByStockIdAndProductIdIn(UUID stockId, List<UUID> productIds);
}
