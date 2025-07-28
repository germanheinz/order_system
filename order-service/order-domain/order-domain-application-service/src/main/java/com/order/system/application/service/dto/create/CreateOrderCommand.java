package com.order.system.application.service.dto.create;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Builder
@NoArgsConstructor(force = true)
public class CreateOrderCommand {
    
    private final UUID customerId;
    
    private final UUID stockId;
    
    private final BigDecimal price;
    
    private final List<OrderItem> items;
    
    private final OrderAddress address;

    public CreateOrderCommand(UUID customerId, UUID stockId, BigDecimal price, List<OrderItem> items, OrderAddress address) {
        this.customerId = customerId;
        this.stockId = stockId;
        this.price = price;
        this.items = items;
        this.address = address;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getStockId() {
        return stockId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderAddress getAddress() {
        return address;
    }
}
