package com.cts.cosmos.orderservice.validator;

import com.cts.cosmos.orderservice.advice.OrderId;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Controller
public class OrderIdValidator implements ConstraintValidator<OrderId, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasLength(s) && s.trim().length() > 1;
    }
}
