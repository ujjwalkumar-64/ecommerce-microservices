package com.microservice.payment.payment;

import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    Integer createPayment(PaymentRequest paymentRequest);
}
