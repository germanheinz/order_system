package com.order.common.application;


import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(force = true)
public class ErrorDTO {
    private final String code;
    private final String message;

    public ErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
