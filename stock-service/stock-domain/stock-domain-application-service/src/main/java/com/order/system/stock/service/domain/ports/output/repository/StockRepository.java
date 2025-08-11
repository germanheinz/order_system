package com.order.system.stock.service.domain.ports.output.repository;

import com.order.system.stock.service.domain.entity.Stock;

import java.util.Optional;

public interface StockRepository {
    Optional<Stock> findStockInformation(Stock stock);
}
