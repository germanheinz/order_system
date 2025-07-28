package com.order.system.domain.core.entity;

import com.order.system.domain.entity.AggregateRoot;
import com.order.system.domain.valueobject.StockId;

import java.util.List;

public class Stock extends AggregateRoot<StockId> {
    private final List<Product> products;
    private boolean active;

    private Stock(Builder builder) {
        super.setId(builder.stockId);
        products = builder.products;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private StockId stockId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public Builder stockId(StockId val) {
            stockId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }
    }
}
