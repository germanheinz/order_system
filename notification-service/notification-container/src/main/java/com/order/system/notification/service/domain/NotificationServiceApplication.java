package com.order.system.notification.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = {"com.order.system.notification.service.data" })
@EntityScan(basePackages = { "com.order.system.notification.service.data" })
@SpringBootApplication(scanBasePackages = {"com.order.system","com.order.system.notification.service.domain"})
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
