package com.order.system.stock.service.domain.exception;


import com.order.system.domain.exception.DomainException;

public class StockDomainException extends DomainException {
    public StockDomainException(String message) {
        super(message);
    }

    public StockDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
