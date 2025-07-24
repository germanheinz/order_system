package com.order.system.stock.service.domain.ports.output.repository;

import com.order.system.stock.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
