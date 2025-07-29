package com.order.system.data.stock.mapper;


import com.order.system.common.data.stock.entity.StockEntity;
import com.order.system.common.data.stock.exception.StockDataAccessException;
import com.order.system.domain.core.entity.Product;
import com.order.system.domain.core.entity.Stock;
import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.ProductId;
import com.order.system.domain.valueobject.StockId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StockDataAccessMapper {

    public List<UUID> stockToStockProducts(Stock stock) {
        return stock.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Stock stockEntityToStock(List<StockEntity> stockEntities) {
        StockEntity stockEntity =
                stockEntities.stream().findFirst().orElseThrow(() ->
                        new StockDataAccessException("Stock could not be found!"));

        List<Product> stockProducts = stockEntities.stream().map(entity ->
                new Product(new ProductId(entity.getProductId()), entity.getProductName(),
                        new Money(entity.getProductPrice()))).toList();

        return Stock.builder()
                .stockId(new StockId(stockEntity.getStockId()))
                .products(stockProducts)
                .active(stockEntity.getStockActive())
                .build();
    }
}
