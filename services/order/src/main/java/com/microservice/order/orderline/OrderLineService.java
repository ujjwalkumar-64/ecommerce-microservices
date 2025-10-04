package com.microservice.order.orderline;

import org.springframework.stereotype.Service;

@Service
public interface OrderLineService {
     Integer saveOrderLine(OrderLineRequest orderLineRequest);
}
