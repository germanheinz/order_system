package com.order.system.stock.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "stock-service")
public class StockServiceConfigData {
    private String stockApprovalRequestTopicName;
    private String stockApprovalResponseTopicName;
}
