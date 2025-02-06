
package com.order.system.domain.core.valueobject;


import lombok.Getter;

import java.util.UUID;

@Getter
public class StreetAddress {

    private final UUID id;
    private final String street;
    private final String postalCode;
    private final String city;


    public StreetAddress(UUID id, UUID id1, String street, String postalCode, String city) {
        this.id=id;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public StreetAddress(UUID id, String street, String postalCode, String city) {
        this.id = id;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
