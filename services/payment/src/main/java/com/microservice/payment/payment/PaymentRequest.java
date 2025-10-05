package com.microservice.payment.payment;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        Integer orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Customer customer,
        String OrderReference

) {
}
