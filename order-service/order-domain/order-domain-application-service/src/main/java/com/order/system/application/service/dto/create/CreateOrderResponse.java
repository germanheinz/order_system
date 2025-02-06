package com.order.system.application.service.dto.create;

import com.order.system.domain.valueobject.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
    private final UUID orderTrackingId;
    private final OrderStatus orderStatus;
    private final String message;
}