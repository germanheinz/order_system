package com.order.system.application.service.dto.track;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackOrderQuery {
    private UUID orderTrackingId;

    public UUID getOrderTrackingId() {
        return orderTrackingId;
    }

    public void setOrderTrackingId(UUID orderTrackingId) {
        this.orderTrackingId = orderTrackingId;
    }
}
