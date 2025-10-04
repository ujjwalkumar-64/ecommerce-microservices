package com.microservice.order.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest orderRequest) {
         return Order.builder()
                 .id(orderRequest.id())
                 .customerId(orderRequest.customerId())
                 .reference(orderRequest.reference())
                 .totalAmount(orderRequest.totalAmount())
                 .paymentMethod(orderRequest.paymentMethod())
                 .build();

    }

    public OrderResponse fromOrder(Order order) {
        return  OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .reference(order.getReference())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .build();
    }
}
