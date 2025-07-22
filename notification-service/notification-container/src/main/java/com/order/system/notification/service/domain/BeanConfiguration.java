package com.order.system.notification.service.domain;

import com.order.system.restaurant.service.domain.RestaurantDomainService;
import com.order.system.restaurant.service.domain.RestaurantDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public RestaurantDomainService restaurantDomainService() {
        return new RestaurantDomainServiceImpl();
    }
}
