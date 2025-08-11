package com.order.system.application.service.dto.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
public class OrderItem {

    private final UUID productId;
    private final int quantity;
    private final BigDecimal price;
    private final BigDecimal subTotal;

    @JsonCreator
    public OrderItem(
            @JsonProperty("productId") UUID productId,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("price") String price,
            @JsonProperty("subTotal") String subTotal) {
        this(
                productId,
                quantity,
                (price != null) ? new BigDecimal(price) : null,
                (subTotal != null) ? new BigDecimal(subTotal) : null
        );
    }

    public OrderItem(UUID productId, int quantity, BigDecimal price, BigDecimal subTotal) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }

    public OrderItem(UUID productId, int quantity, Number price, Number subTotal) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = (price != null) ? BigDecimal.valueOf(price.doubleValue()) : null;
        this.subTotal = (subTotal != null) ? BigDecimal.valueOf(subTotal.doubleValue()) : null;
    }
}
