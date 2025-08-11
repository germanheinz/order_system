package com.order.system.stock.service.domain.mapper;

import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.OrderId;
import com.order.system.domain.valueobject.OrderStatus;
import com.order.system.domain.valueobject.StockId;
import com.order.system.stock.service.domain.dto.StockApprovalRequest;
import com.order.system.stock.service.domain.entity.OrderDetail;
import com.order.system.stock.service.domain.entity.Product;
import com.order.system.stock.service.domain.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StockDataMapper {
    public Stock stockApprovalRequestToStock(StockApprovalRequest
                                                               stockApprovalRequest) {
        return Stock.builder()
                .stockId(new StockId(UUID.fromString(stockApprovalRequest.getStockId())))
                .orderDetail(OrderDetail.builder()
                        .orderId(new OrderId(UUID.fromString(stockApprovalRequest.getOrderId())))
                        .products(stockApprovalRequest.getProducts().stream().map(
                                        product -> Product.builder()
                                                .productId(product.getId())
                                                .quantity(product.getQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                        .totalAmount(new Money(stockApprovalRequest.getPrice()))
                        .orderStatus(OrderStatus.valueOf(stockApprovalRequest.getStockOrderStatus().name()))
                        .build())
                .build();
    }
}