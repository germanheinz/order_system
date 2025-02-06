
package com.order.system.domain.core.valueobject;

import com.order.system.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID id){
        super(id);
    }
}
