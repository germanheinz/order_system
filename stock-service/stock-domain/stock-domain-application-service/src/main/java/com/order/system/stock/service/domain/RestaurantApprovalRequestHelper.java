package com.order.system.stock.service.domain;

import com.order.system.domain.valueobject.OrderId;
import com.order.system.stock.service.domain.dto.RestaurantApprovalRequest;
import com.order.system.stock.service.domain.entity.Stock;
import com.order.system.stock.service.domain.event.OrderApprovalEvent;
import com.order.system.stock.service.domain.exception.RestaurantNotFoundException;
import com.order.system.stock.service.domain.mapper.RestaurantDataMapper;
import com.order.system.stock.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
import com.order.system.stock.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import com.order.system.stock.service.domain.ports.output.repository.OrderApprovalRepository;
import com.order.system.stock.service.domain.ports.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class RestaurantApprovalRequestHelper {

    private final stockDomainService stockDomainService;
    private final RestaurantDataMapper restaurantDataMapper;
    private final RestaurantRepository restaurantRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;
    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;

    public RestaurantApprovalRequestHelper(stockDomainService stockDomainService,
                                           RestaurantDataMapper restaurantDataMapper,
                                           RestaurantRepository restaurantRepository,
                                           OrderApprovalRepository orderApprovalRepository,
                                           OrderApprovedMessagePublisher orderApprovedMessagePublisher,
                                           OrderRejectedMessagePublisher orderRejectedMessagePublisher
    ) {
        this.stockDomainService = stockDomainService;
        this.restaurantDataMapper = restaurantDataMapper;
        this.restaurantRepository = restaurantRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.orderApprovedMessagePublisher = orderApprovedMessagePublisher;
        this.orderRejectedMessagePublisher = orderRejectedMessagePublisher;
    }

    @Transactional
    public OrderApprovalEvent persistOrderApproval(RestaurantApprovalRequest restaurantApprovalRequest) {
        log.info("Processing stock approval for order id: {}", restaurantApprovalRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        Stock stock = findRestaurant(restaurantApprovalRequest);
        OrderApprovalEvent orderApprovalEvent =
                stockDomainService.validateOrder(
                        stock,
                        failureMessages,
                        orderApprovedMessagePublisher,
                        orderRejectedMessagePublisher
                );
        orderApprovalRepository.save(stock.getOrderApproval());
        return orderApprovalEvent;
    }

    private Stock findRestaurant(RestaurantApprovalRequest restaurantApprovalRequest) {
        Stock stock = restaurantDataMapper
                .restaurantApprovalRequestToRestaurant(restaurantApprovalRequest);
        Optional<Stock> restaurantResult = restaurantRepository.findRestaurantInformation(stock);
        if (restaurantResult.isEmpty()) {
            log.error("Stock with id " + stock.getId().getValue() + " not found!");
            throw new RestaurantNotFoundException("Stock with id " + stock.getId().getValue() +
                    " not found!");
        }

        Stock restaurantEntity = restaurantResult.get();
        stock.setActive(restaurantEntity.isActive());
        stock.getOrderDetail().getProducts().forEach(product ->
                restaurantEntity.getOrderDetail().getProducts().forEach(p -> {
            if (p.getId().equals(product.getId())) {
                product.updateWithConfirmedNamePriceAndAvailability(p.getName(), p.getPrice(), p.isAvailable());
            }
        }));
        stock.getOrderDetail().setId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())));

        return stock;
    }
}
