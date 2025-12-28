package com.microservice.payment.kafka;

import com.microservice.payment.payment.PaymentRequest;
import com.microservice.payment.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentConsumer {
    private final PaymentService paymentService;

    @KafkaListener(topics = "payment-request-topic",groupId = "paymentRequestGroup")
    public void consumePaymentRequest(PaymentRequest paymentRequest) {
        log.info("Consume message from payment-request-topic :: {}", paymentRequest);
        try{
            paymentService.createPayment(paymentRequest);

        }
        catch (Exception e){
            log.warn("Error while sending order confirmation email :: {}", paymentRequest);
        }
    }
}
