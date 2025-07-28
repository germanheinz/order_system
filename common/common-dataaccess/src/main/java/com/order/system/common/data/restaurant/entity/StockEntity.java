package com.order.system.common.data.restaurant.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StockEntityId.class)
@Table(name = "order_stock_m_view", schema = "stock")
@Entity
public class StockEntity {

    @Id
    private UUID stockId;
    @Id
    private UUID productId;
    private String stockName;
    private Boolean stockActive;
    private String productName;
    private BigDecimal productPrice;
    private Boolean productAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity that = (StockEntity) o;
        return stockId.equals(that.stockId) && productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, productId);
    }
}
