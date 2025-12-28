package com.microservice.order.kafka;


import com.microservice.order.payment.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {
    private final KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    public void sendPaymentRequest(PaymentRequest paymentRequest){
        log.info("Sending payment request ");
        Message<PaymentRequest> message= MessageBuilder
                .withPayload(paymentRequest)
                .setHeader(KafkaHeaders.TOPIC,"payment-request-topic")
                .build();

        // now to produce message from payment to kafka message broker
        kafkaTemplate.send(message);
    }
}
