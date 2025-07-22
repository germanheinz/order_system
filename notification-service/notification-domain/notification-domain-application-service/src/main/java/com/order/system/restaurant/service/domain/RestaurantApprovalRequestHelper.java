package com.order.system.restaurant.service.domain;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.domain.valueobject.OrderId;
import com.order.system.restaurant.service.domain.dto.RestaurantApprovalRequest;
import com.order.system.restaurant.service.domain.entity.Restaurant;
import com.order.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.order.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.order.system.restaurant.service.domain.event.OrderRejectedEvent;
import com.order.system.restaurant.service.domain.exception.RestaurantNotFoundException;
import com.order.system.restaurant.service.domain.mapper.RestaurantDataMapper;
import com.order.system.restaurant.service.domain.ports.output.message.publish.payment.OrderNotificationFailedRequestMessagePublisher;
import com.order.system.restaurant.service.domain.ports.output.message.publish.payment.OrderNotificationSentRequestMessagePublisher;
import com.order.system.restaurant.service.domain.ports.output.repository.OrderApprovalRepository;
import com.order.system.restaurant.service.domain.ports.output.repository.RestaurantRepository;
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

    private final RestaurantDomainService restaurantDomainService;
    private final RestaurantDataMapper restaurantDataMapper;
    private final RestaurantRepository restaurantRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher;
    private final DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher;

    public RestaurantApprovalRequestHelper(RestaurantDomainService restaurantDomainService,
                                           RestaurantDataMapper restaurantDataMapper,
                                           RestaurantRepository restaurantRepository,
                                           OrderApprovalRepository orderApprovalRepository,
                                           DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
                                           DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher) {
        this.restaurantDomainService = restaurantDomainService;
        this.restaurantDataMapper = restaurantDataMapper;
        this.restaurantRepository = restaurantRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.orderApprovedEventDomainEventPublisher = orderApprovedEventDomainEventPublisher;
        this.orderRejectedEventDomainEventPublisher = orderRejectedEventDomainEventPublisher;
    }

    @Transactional
    public OrderApprovalEvent persistOrderApproval(RestaurantApprovalRequest restaurantApprovalRequest) {
        log.info("Processing restaurant approval for order id: {}", restaurantApprovalRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        Restaurant restaurant = findRestaurant(restaurantApprovalRequest);
        OrderApprovalEvent orderApprovalEvent =
                restaurantDomainService.validateOrder(
                        restaurant,
                        failureMessages,
                        orderApprovedEventDomainEventPublisher,
                        orderRejectedEventDomainEventPublisher);
        orderApprovalRepository.save(restaurant.getOrderApproval());
        return orderApprovalEvent;
    }

    private Restaurant findRestaurant(RestaurantApprovalRequest restaurantApprovalRequest) {
        Restaurant restaurant = restaurantDataMapper
                .restaurantApprovalRequestToRestaurant(restaurantApprovalRequest);
        Optional<Restaurant> restaurantResult = restaurantRepository.findRestaurantInformation(restaurant);
        if (restaurantResult.isEmpty()) {
            log.error("Restaurant with id " + restaurant.getId().getValue() + " not found!");
            throw new RestaurantNotFoundException("Restaurant with id " + restaurant.getId().getValue() +
                    " not found!");
        }

        Restaurant restaurantEntity = restaurantResult.get();
        restaurant.setActive(restaurantEntity.isActive());
        restaurant.getOrderDetail().getProducts().forEach(product ->
                restaurantEntity.getOrderDetail().getProducts().forEach(p -> {
            if (p.getId().equals(product.getId())) {
                product.updateWithConfirmedNamePriceAndAvailability(p.getName(), p.getPrice(), p.isAvailable());
            }
        }));
        restaurant.getOrderDetail().setId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())));

        return restaurant;
    }
}
