package com.microservice.order.kafka;

import com.microservice.order.customer.CustomerResponse;
import com.microservice.order.order.PaymentMethod;
import com.microservice.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}
