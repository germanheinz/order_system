package com.order.system.domain.event;

public interface DomainEvent <T>{
    void fire();
}
