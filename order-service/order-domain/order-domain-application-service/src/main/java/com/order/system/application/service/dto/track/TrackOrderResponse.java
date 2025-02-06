package com.order.system.application.service.dto.track;

import com.order.system.domain.valueobject.OrderStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackOrderResponse {
    
    private final UUID orderTrackingId;
    
    private final OrderStatus orderStatus;
    private final List<String> failureMessages;

    public TrackOrderResponse(List<String> failureMessages, OrderStatus orderStatus, UUID orderTrackingId) {
        this.failureMessages = failureMessages;
        this.orderStatus = orderStatus;
        this.orderTrackingId = orderTrackingId;
    }
}
