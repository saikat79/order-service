package com.cts.cosmos.orderservice.service;

import com.cts.cosmos.orderservice.dao.OrderServiceDAO;
import com.cts.cosmos.orderservice.exception.ValidationException;
import com.cts.cosmos.orderservice.model.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
public class OrderService {

    @Autowired
    private OrderServiceDAO dao;

    public Order createOrder(Order order) {
        log.info("service.createOrder->{}", order.getOrderId());
        if (dao.readOrder(order.getOrderId()) != null) {
            throw new ValidationException(String.format("OrderId %s already exists !", order.getOrderId()));
        }
        return dao.createOrder(order);
    }
}
