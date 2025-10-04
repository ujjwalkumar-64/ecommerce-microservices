package com.microservice.order.order;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
}
