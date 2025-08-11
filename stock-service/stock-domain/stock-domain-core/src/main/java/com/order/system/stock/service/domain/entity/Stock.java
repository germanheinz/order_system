package com.order.system.stock.service.domain.entity;


import com.order.system.stock.service.domain.valueobject.OrderApprovalId;
import com.order.system.domain.entity.AggregateRoot;
import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.OrderApprovalStatus;
import com.order.system.domain.valueobject.OrderStatus;
import com.order.system.domain.valueobject.StockId;

import java.util.List;
import java.util.UUID;

public class Stock extends AggregateRoot<StockId> {
   private OrderApproval orderApproval;
   private boolean active;
   private final OrderDetail orderDetail;

   public void validateOrder(List<String> failureMessages) {
       if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
           failureMessages.add("Payment is not completed for order: " + orderDetail.getId());
       }
       Money totalAmount = orderDetail.getProducts().stream().map(product -> {
           if (!product.isAvailable()) {
               failureMessages.add("Product with id: " + product.getId().getValue()
                       + " is not available");
           }
           if(product.getPrice() == null){
               product.setPrice(Money.ZERO);
           }
           return product.getPrice().multiply(product.getQuantity());
       }).reduce(Money.ZERO, Money::add);

       if (!totalAmount.equals(orderDetail.getTotalAmount())) {
           failureMessages.add("Price total is not correct for order: " + orderDetail.getId());
       }
   }

   public void constructOrderApproval(OrderApprovalStatus orderApprovalStatus) {
       this.orderApproval = OrderApproval.builder()
               .orderApprovalId(new OrderApprovalId(UUID.randomUUID()))
               .stockId(this.getId())
               .orderId(this.getOrderDetail().getId())
               .approvalStatus(orderApprovalStatus)
               .build();
   }

    public void setActive(boolean active) {
        this.active = active;
    }

    private Stock(Builder builder) {
        setId(builder.stockId);
        orderApproval = builder.orderApproval;
        active = builder.active;
        orderDetail = builder.orderDetail;
    }

    public static Builder builder() {
        return new Builder();
    }

    public OrderApproval getOrderApproval() {
        return orderApproval;
    }

    public boolean isActive() {
        return active;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public static final class Builder {
        private StockId stockId;
        private OrderApproval orderApproval;
        private boolean active;
        private OrderDetail orderDetail;

        private Builder() {
        }

        public Builder stockId(StockId val) {
            stockId = val;
            return this;
        }

        public Builder orderApproval(OrderApproval val) {
            orderApproval = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Builder orderDetail(OrderDetail val) {
            orderDetail = val;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }
    }
}
