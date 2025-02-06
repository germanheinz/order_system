package com.order.system.application.service.dto.create;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderItem {
    private final UUID productId;
    private final Integer quantity;
    private final BigDecimal price;
    private final BigDecimal subTotal;

}

