package com.order.system.application.service.ports.output.repository;


import com.order.system.domain.core.entity.Stock;

import java.util.Optional;

public interface StockRepository {

    Optional<Stock> findStockInformation(Stock stock);
}
