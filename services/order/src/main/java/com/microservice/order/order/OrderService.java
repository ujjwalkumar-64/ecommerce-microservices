package com.microservice.order.order;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    List<OrderResponse> findAllOrders();
    OrderResponse findOrderById(Integer orderId);

}
