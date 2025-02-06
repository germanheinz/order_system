package com.order.system.application.service.ports.output.repository;


import com.order.system.domain.core.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
