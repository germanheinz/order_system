package com.order.system.stock.service.data.entity;

import com.order.system.domain.valueobject.OrderApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_approval", schema = "stock")
@Entity
public class OrderApprovalEntity {

    @Id
    private UUID id;
    private UUID stockId;
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private OrderApprovalStatus status;
}
