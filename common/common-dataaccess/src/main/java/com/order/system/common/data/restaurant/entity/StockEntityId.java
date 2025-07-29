package com.order.system.common.data.stock.entity;



import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockEntityId implements Serializable {

    private UUID stockId;
    private UUID productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntityId that = (StockEntityId) o;
        return stockId.equals(that.stockId) && productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, productId);
    }
}
