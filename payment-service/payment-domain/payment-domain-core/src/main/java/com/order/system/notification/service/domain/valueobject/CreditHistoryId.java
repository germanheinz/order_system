package com.order.system.notification.service.domain.valueobject;

import com.order.system.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditHistoryId extends BaseId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}
