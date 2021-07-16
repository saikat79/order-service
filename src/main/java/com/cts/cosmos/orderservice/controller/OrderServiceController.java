package com.cts.cosmos.orderservice.controller;

import com.cts.cosmos.orderservice.advice.LogExecutionTime;
import com.cts.cosmos.orderservice.model.Order;
import com.cts.cosmos.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order-service")
@ControllerAdvice
@Log4j2
public class OrderServiceController {

    @Autowired
    private OrderService service;

    @PostMapping(value = "/orders")
    @LogExecutionTime
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        log.info("controller.createOrder->{}", order);
        Order data = service.createOrder(order);
        if (data != null) {
            return new ResponseEntity<>(data, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
