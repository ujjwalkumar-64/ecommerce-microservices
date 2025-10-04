package com.microservice.order.orderline;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderLineService {
     Integer saveOrderLine(OrderLineRequest orderLineRequest);
     List<OrderLineResponse> findAllByOrderId(Integer orderId);
}
