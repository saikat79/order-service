package com.cts.cosmos.orderservice.dao;

import com.cts.cosmos.orderservice.advice.LogExecutionTime;
import com.cts.cosmos.orderservice.model.Order;
import com.cts.cosmos.orderservice.util.AppConstants;
import com.cts.cosmos.orderservice.util.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.cosmosdb.Document;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
public class OrderServiceDAO extends BaseDBDAO {

    private ObjectMapper mapper = CommonUtils.getMapper();

    @LogExecutionTime
    public Order createOrder(Order order) {
        try {
            log.info("dao.createOrder->{}", order.getOrderId());
            super.create(AppConstants.ORDERS_COLLECTION_ID,
                    order.getOrderId(), new Document(mapper.writeValueAsString(order)));
        } catch (IOException e) {
            log.error("Unable to save orders | Exception: {}", ExceptionUtils.getStackTrace(e));
        }
        return order;
    }

    @LogExecutionTime
    public Order readOrder(String orderId) {
        Order order = null;
        try {
            final List<Document> docs = super.read(AppConstants.ORDERS_COLLECTION_ID, String.format("SELECT * FROM c where c.orderId = '%s'", orderId));
            if (CollectionUtils.isNotEmpty(docs)) {
                order = mapper.readValue(docs.get(0).toString(), Order.class);
            }
        } catch (IOException e) {
            log.error("Unable to read order | Exception: {}", ExceptionUtils.getStackTrace(e));
        }
        return order;
    }
}
