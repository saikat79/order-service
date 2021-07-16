package com.cts.cosmos.orderservice.model;

import lombok.Data;

@Data
public class ErrorResponse {

    private ErrorVO error = new ErrorVO();

    @Data
    public static class ErrorVO {
        private String code;
        private String message;
    }

}
