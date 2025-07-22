package com.order.system.restaurant.service.domain.ports.output.repository;


import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    //todo cambiarlo customer
    Optional<?> findCustomer(UUID customerId);
}
