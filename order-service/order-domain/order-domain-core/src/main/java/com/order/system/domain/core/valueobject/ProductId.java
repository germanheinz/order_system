package com.order.system.domain.core.valueobject;

import com.order.system.domain.valueobject.BaseId;

import java.util.UUID;

public class ProductId extends BaseId<UUID> {
    public ProductId(UUID value) {
        super(value);
    }
}
