package com.microservice.order.payment;

import com.microservice.order.customer.CustomerResponse;
import com.microservice.order.order.PaymentMethod;
import jakarta.validation.constraints.Positive;
import lombok.Builder;


import java.math.BigDecimal;

public record PaymentRequest(

        Integer orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        String OrderReference
) {

}
