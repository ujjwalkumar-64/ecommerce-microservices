package com.microservice.payment.payment;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Integer> payment(@RequestBody @Valid PaymentRequest paymentRequest){
        return  ResponseEntity.ok(paymentService.createPayment(paymentRequest));
    }
}
