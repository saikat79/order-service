package com.cts.cosmos.orderservice.advice;

import com.cts.cosmos.orderservice.validator.OrderIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderIdValidator.class)
@Documented
public @interface OrderId {

    String message() default "OrderId is not valid, it should be more than 1 char length.";
    
    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};

}
