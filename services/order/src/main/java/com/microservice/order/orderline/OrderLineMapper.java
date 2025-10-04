package com.microservice.order.orderline;

import com.microservice.order.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine  toOrderLine(OrderLineRequest orderLineRequest){
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .productId(orderLineRequest.productId())
                .order(
                        Order.builder()
                                .id(orderLineRequest.orderId())
                                .build()
                )
                .quantity(orderLineRequest.quantity())
                .build();
    }
}
