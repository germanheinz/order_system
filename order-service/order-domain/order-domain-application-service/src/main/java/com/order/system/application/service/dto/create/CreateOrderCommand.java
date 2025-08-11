package com.order.system.application.service.dto.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
public class CreateOrderCommand {

    private final UUID customerId;
    private final UUID stockId;
    private final BigDecimal price;
    private final List<OrderItem> items;
    private final OrderAddress address;

    @JsonCreator
    public CreateOrderCommand(
            @JsonProperty("customerId") UUID customerId,
            @JsonProperty("stockId") UUID stockId,
            @JsonProperty("price") String price,
            @JsonProperty("items") List<OrderItem> items,
            @JsonProperty("address") OrderAddress address) {

        this(customerId, stockId, (price != null) ? new BigDecimal(price) : null, items, address);
    }

    public CreateOrderCommand(UUID customerId, UUID stockId, BigDecimal price, List<OrderItem> items, OrderAddress address) {
        this.customerId = customerId;
        this.stockId = stockId;
        this.price = price;
        this.items = items;
        this.address = address;
    }

}
