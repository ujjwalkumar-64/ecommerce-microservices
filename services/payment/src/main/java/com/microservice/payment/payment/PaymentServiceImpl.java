package com.microservice.payment.payment;

import com.microservice.payment.kafka.NotificationProducer;
import com.microservice.payment.kafka.PaymentNotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private NotificationProducer notificationProducer;

    @Override
    public Integer createPayment(PaymentRequest paymentRequest) {
        var payment = paymentRepository.save(paymentMapper.toPayment(paymentRequest));
        // todo : integrate razorpay
        // send to notification server-- kafka
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.OrderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstName(),
                        paymentRequest.customer().lastName(),
                        paymentRequest.customer().email()
                )
        );


        return payment.getId();
    }
}
