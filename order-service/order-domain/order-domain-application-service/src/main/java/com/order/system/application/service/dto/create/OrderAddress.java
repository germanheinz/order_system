package com.order.system.application.service.dto.create;

import lombok.*;


@Builder
@Data
@AllArgsConstructor
public class OrderAddress {
    private final String street;
    private final String postalCode;
    private final String city;


    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
