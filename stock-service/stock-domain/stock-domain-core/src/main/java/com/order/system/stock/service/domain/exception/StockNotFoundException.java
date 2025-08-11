package com.order.system.stock.service.domain.exception;


import com.order.system.domain.exception.DomainException;

public class StockNotFoundException extends DomainException {
    public StockNotFoundException(String message) {
        super(message);
    }

    public StockNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
