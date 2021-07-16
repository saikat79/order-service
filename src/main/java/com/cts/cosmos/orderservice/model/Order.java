
package com.cts.cosmos.orderservice.model;

import com.cts.cosmos.orderservice.advice.OrderId;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Order
 * <p>
 * An Order model
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Order {
    @OrderId
    @NotEmpty(message = "OrderId should not be empty")
    private String orderId;
    private String tempOrderId;
    private String orderCost;
    @NotEmpty(message = "OrderDate should not be empty")
    private String orderDate;
    private Boolean isOrderDuplicate;
    private Boolean isOrderSuccessfullyPlaced;
    private Address addressOfCustomer;
    private Address addressOfSeller;
}
