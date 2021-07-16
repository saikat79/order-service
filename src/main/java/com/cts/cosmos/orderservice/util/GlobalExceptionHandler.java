package com.cts.cosmos.orderservice.util;

import com.cts.cosmos.orderservice.exception.ValidationException;
import com.cts.cosmos.orderservice.model.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice(annotations = ControllerAdvice.class)
@Order(1)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleAllExceptions(ex, request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        final String exceptionType = ex.getClass().getName();

        log.warn("Request resulted in an error of type: {} ", exceptionType);

        final ErrorResponse response = new ErrorResponse();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String message = ex.getMessage();
        if (exceptionType.equals(MethodArgumentNotValidException.class.getName())) {
            response.getError().setCode("400");
            status = HttpStatus.BAD_REQUEST;
            message = message.replaceAll("]", "");
            String messageLoc = "default message [";
            int i = message.lastIndexOf(messageLoc);
            if (i > 0) {
                message = message.substring(i + messageLoc.length()).trim();
            }
        } else if (exceptionType.equals(ValidationException.class.getName())) {
            response.getError().setCode("406");
            message = ((ValidationException)ex).getMessage();
        } else {
            response.getError().setCode("500");
        }
        response.getError().setMessage(message);
        return ResponseEntity.status(status).headers(headers).body(response);
    }


}
