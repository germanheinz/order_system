package com.order.system.stock.service.data.adapter;

import com.order.system.common.data.stock.entity.StockEntity;
import com.order.system.common.data.stock.repository.StockJpaRepository;
import com.order.system.stock.service.data.mapper.RestaurantDataAccessMapper;
import com.order.system.stock.service.domain.entity.Stock;
import com.order.system.stock.service.domain.ports.output.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final StockJpaRepository stockJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public RestaurantRepositoryImpl(StockJpaRepository stockJpaRepository,
                                    RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.stockJpaRepository = stockJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public Optional<Stock> findRestaurantInformation(Stock stock) {
        List<UUID> restaurantProducts =
                restaurantDataAccessMapper.restaurantToRestaurantProducts(stock);
        Optional<List<StockEntity>> restaurantEntities = stockJpaRepository
                .findByStockIdAndProductIdIn(stock.getId().getValue(),
                        restaurantProducts);
        return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
