package com.order.system.stock.service.domain;

import com.order.system.domain.valueobject.OrderId;
import com.order.system.stock.service.domain.dto.StockApprovalRequest;
import com.order.system.stock.service.domain.entity.Stock;
import com.order.system.stock.service.domain.event.OrderApprovalEvent;
import com.order.system.stock.service.domain.exception.StockNotFoundException;
import com.order.system.stock.service.domain.mapper.StockDataMapper;
import com.order.system.stock.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
import com.order.system.stock.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import com.order.system.stock.service.domain.ports.output.repository.OrderApprovalRepository;
import com.order.system.stock.service.domain.ports.output.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class StockApprovalRequestHelper {

    private final stockDomainService stockDomainService;
    private final StockDataMapper stockDataMapper;
    private final StockRepository stockRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;
    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;

    public StockApprovalRequestHelper(stockDomainService stockDomainService,
                                      StockDataMapper stockDataMapper,
                                      StockRepository stockRepository,
                                      OrderApprovalRepository orderApprovalRepository,
                                      OrderApprovedMessagePublisher orderApprovedMessagePublisher,
                                      OrderRejectedMessagePublisher orderRejectedMessagePublisher
    ) {
        this.stockDomainService = stockDomainService;
        this.stockDataMapper = stockDataMapper;
        this.stockRepository = stockRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.orderApprovedMessagePublisher = orderApprovedMessagePublisher;
        this.orderRejectedMessagePublisher = orderRejectedMessagePublisher;
    }

    @Transactional
    public OrderApprovalEvent persistOrderApproval(StockApprovalRequest stockApprovalRequest) {
        log.info("Processing stock approval for order id: {}", stockApprovalRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        Stock stock = findStock(stockApprovalRequest);
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

    private Stock findStock(StockApprovalRequest stockApprovalRequest) {
        Stock stock = stockDataMapper
                .stockApprovalRequestToStock(stockApprovalRequest);
        Optional<Stock> stockResult = stockRepository.findStockInformation(stock);
        if (stockResult.isEmpty()) {
            log.error("Stock with id " + stock.getId().getValue() + " not found!");
            throw new StockNotFoundException("Stock with id " + stock.getId().getValue() +
                    " not found!");
        }

        Stock stockEntity = stockResult.get();
        stock.setActive(stockEntity.isActive());
        stock.getOrderDetail().getProducts().forEach(product ->
                stockEntity.getOrderDetail().getProducts().forEach(p -> {
            if (p.getId().equals(product.getId())) {
                product.updateWithConfirmedNamePriceAndAvailability(p.getName(), p.getPrice(), p.isAvailable());
            }
        }));
        stock.getOrderDetail().setId(new OrderId(UUID.fromString(stockApprovalRequest.getOrderId())));

        return stock;
    }
}
