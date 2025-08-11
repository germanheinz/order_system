package com.order.system.stock.service.domain.exception;

import com.order.system.domain.exception.DomainException;

public class StockApplicationServiceException extends DomainException {
    public StockApplicationServiceException(String message) {
        super(message);
    }

    public StockApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
