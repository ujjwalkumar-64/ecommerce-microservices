package com.microservice.notification.kafka;

import com.microservice.notification.email.EmailService;
import com.microservice.notification.kafka.order.OrderConfirmation;
import com.microservice.notification.kafka.payment.PaymentConfirmation;
import com.microservice.notification.notification.Notification;
import com.microservice.notification.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.microservice.notification.notification.NotificationType.ORDER_CONFIRMATION;
import static com.microservice.notification.notification.NotificationType.PAYMENT_CONFIRMATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consume message from order-topic :: {}", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .notificationDateTime(LocalDateTime.now())
                        .notificationType(ORDER_CONFIRMATION)
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customerName= orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();

        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()

        );

    }

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentConfirmationNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consume message from payment-topic :: {}", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .notificationDateTime(LocalDateTime.now())
                        .notificationType(PAYMENT_CONFIRMATION)
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstName()+" "+paymentConfirmation.customerLastName();

        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()

        );
    }
}
