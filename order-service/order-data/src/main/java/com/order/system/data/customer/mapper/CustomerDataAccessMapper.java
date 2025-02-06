package com.order.system.data.customer.mapper;

import com.order.system.domain.valueobject.CustomerId;
import com.order.system.data.customer.entity.CustomerEntity;
import com.order.system.domain.core.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(UUID.fromString(customer.getId().toString()))
                .username(customer.getUsername())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }
}
