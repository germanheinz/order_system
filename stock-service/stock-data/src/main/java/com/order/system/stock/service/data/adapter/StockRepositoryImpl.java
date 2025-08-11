package com.order.system.stock.service.data.adapter;

import com.order.system.common.data.stock.entity.StockEntity;
import com.order.system.common.data.stock.repository.StockJpaRepository;
import com.order.system.stock.service.data.mapper.StockDataAccessMapper;
import com.order.system.stock.service.domain.entity.Stock;
import com.order.system.stock.service.domain.ports.output.repository.StockRepository;
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
        Optional<List<StockEntity>> stockEntities = stockJpaRepository
                .findByStockIdAndProductIdIn(stock.getId().getValue(),
                        stockProducts);
        return stockEntities.map(stockDataAccessMapper::stockEntityToStock);
    }
}
