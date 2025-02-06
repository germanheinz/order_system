package com.order.system.domain.valueobject;

import java.util.UUID;

public class CustomerId extends BaseId<UUID> {

    public CustomerId(UUID id) {
        super(id);
    }
}
