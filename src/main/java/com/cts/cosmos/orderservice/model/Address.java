
package com.cts.cosmos.orderservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Address {
    @NotEmpty(message = "AddressRow1 should not be empty")
    private String addressRow1;
    private String addressRow2;
    private String addressRow3;
    private String city;
    private String state;
    @NotEmpty(message = "PostalCode should not be empty")
    private String postalCode;
}
