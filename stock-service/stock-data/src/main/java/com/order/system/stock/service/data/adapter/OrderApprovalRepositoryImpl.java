package com.order.system.stock.service.data.adapter;

import com.order.system.stock.service.data.mapper.StockDataAccessMapper;
import com.order.system.stock.service.data.repository.OrderApprovalJpaRepository;
import com.order.system.stock.service.domain.entity.OrderApproval;
import com.order.system.stock.service.domain.ports.output.repository.OrderApprovalRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final StockDataAccessMapper stockDataAccessMapper;

    public OrderApprovalRepositoryImpl(OrderApprovalJpaRepository orderApprovalJpaRepository,
                                       StockDataAccessMapper stockDataAccessMapper) {
        this.orderApprovalJpaRepository = orderApprovalJpaRepository;
        this.stockDataAccessMapper = stockDataAccessMapper;
    }

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        return stockDataAccessMapper
                .orderApprovalEntityToOrderApproval(orderApprovalJpaRepository
                        .save(stockDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval)));
    }

}
