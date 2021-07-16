package com.cts.cosmos.orderservice.exception;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {

    private String message;

    public ValidationException(String msg) {
        this.message = msg;
    }

}
