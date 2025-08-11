package com.order.system.stock.service.domain.dto;

import com.order.system.domain.valueobject.StockOrderStatus;
import com.order.system.stock.service.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StockApprovalRequest {
    private String id;
    private String sagaId;
    private String stockId;
    private String orderId;
    private StockOrderStatus stockOrderStatus;
    private java.util.List<Product> products;
    private java.math.BigDecimal price;
    private java.time.Instant createdAt;
}
