package com.order.system.stock.service.domain.entity;

import com.order.system.stock.service.domain.valueobject.OrderApprovalId;
import com.order.system.domain.entity.BaseEntity;
import com.order.system.domain.valueobject.OrderApprovalStatus;
import com.order.system.domain.valueobject.OrderId;
import com.order.system.domain.valueobject.StockId;

public class OrderApproval extends BaseEntity<OrderApprovalId> {
    private final StockId stockId;
    private final OrderId orderId;
    private final OrderApprovalStatus approvalStatus;

    private OrderApproval(Builder builder) {
        setId(builder.orderApprovalId);
        stockId = builder.stockId;
        orderId = builder.orderId;
        approvalStatus = builder.approvalStatus;
    }

    public static Builder builder() {
        return new Builder();
    }


    public StockId getStockId() {
        return stockId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public OrderApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public static final class Builder {
        private OrderApprovalId orderApprovalId;
        private StockId stockId;
        private OrderId orderId;
        private OrderApprovalStatus approvalStatus;

        private Builder() {
        }

        public Builder orderApprovalId(OrderApprovalId val) {
            orderApprovalId = val;
            return this;
        }

        public Builder stockId(StockId val) {
            stockId = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder approvalStatus(OrderApprovalStatus val) {
            approvalStatus = val;
            return this;
        }

        public OrderApproval build() {
            return new OrderApproval(this);
        }
    }
}
