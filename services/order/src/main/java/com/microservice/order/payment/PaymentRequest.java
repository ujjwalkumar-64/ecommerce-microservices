package com.microservice.order.payment;

import com.microservice.order.customer.CustomerResponse;
import com.microservice.order.order.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record PaymentRequest(
        Integer id,
        Integer orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        String OrderReference
) {
}
