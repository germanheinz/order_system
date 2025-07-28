package com.order.system.data.restaurant.adapter;

import com.order.system.common.data.restaurant.entity.StockEntity;
import com.order.system.common.data.restaurant.repository.StockJpaRepository;
import com.order.system.application.service.ports.output.repository.StockRepository;
import com.order.system.data.restaurant.mapper.StockDataAccessMapper;
import com.order.system.domain.core.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;
    private final StockDataAccessMapper stockDataAccessMapper;

    public StockRepositoryImpl(StockJpaRepository stockJpaRepository,
                               StockDataAccessMapper stockDataAccessMapper) {
        this.stockJpaRepository = stockJpaRepository;
        this.stockDataAccessMapper = stockDataAccessMapper;
    }

    @Override
    public Optional<Stock> findStockInformation(Stock stock) {
        List<UUID> stockProducts =
                stockDataAccessMapper.stockToStockProducts(stock);
        Optional<List<StockEntity>> restaurantEntities = stockJpaRepository
                .findByStockIdAndProductIdIn(stock.getId().getValue(),
                        stockProducts);
        return restaurantEntities.map(stockDataAccessMapper::stockEntityToStock);
    }
}
