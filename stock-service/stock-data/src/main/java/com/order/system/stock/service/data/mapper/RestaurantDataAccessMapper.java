package com.order.system.stock.service.data.mapper;

import com.order.system.common.data.restaurant.entity.StockEntity;
import com.order.system.common.data.restaurant.exception.StockDataAccessException;
import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.OrderId;
import com.order.system.domain.valueobject.ProductId;
import com.order.system.domain.valueobject.StockId;
import com.order.system.stock.service.data.entity.OrderApprovalEntity;
import com.order.system.stock.service.domain.entity.OrderApproval;
import com.order.system.stock.service.domain.entity.OrderDetail;
import com.order.system.stock.service.domain.entity.Product;
import com.order.system.stock.service.domain.entity.Stock;
import com.order.system.stock.service.domain.valueobject.OrderApprovalId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Stock restaurant) {
        return restaurant.getOrderDetail().getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Stock restaurantEntityToRestaurant(List<StockEntity> restaurantEntities) {
        StockEntity stockEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new StockDataAccessException("No restaurants found!"));

        List<Product> restaurantProducts = restaurantEntities.stream().map(entity ->
                        Product.builder()
                                .productId(new ProductId(entity.getProductId()))
                                .name(entity.getProductName())
                                .price(new Money(entity.getProductPrice()))
                                .available(entity.getProductAvailable())
                                .build())
                .collect(Collectors.toList());

        return Stock.builder()
                .stockId(new StockId(stockEntity.getStockId()))
                .orderDetail(OrderDetail.builder()
                        .products(restaurantProducts)
                        .build())
                .active(stockEntity.getStockActive())
                .build();
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .id(orderApproval.getId().getValue())
                .stockId(orderApproval.getStockId().getValue())
                .orderId(orderApproval.getOrderId().getValue())
                .status(orderApproval.getApprovalStatus())
                .build();
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(orderApprovalEntity.getId()))
                .stockId(new StockId(orderApprovalEntity.getStockId()))
                .orderId(new OrderId(orderApprovalEntity.getOrderId()))
                .approvalStatus(orderApprovalEntity.getStatus())
                .build();
    }

}
