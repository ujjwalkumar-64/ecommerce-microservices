package com.microservice.order.orderline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private OrderLineMapper orderLineMapper;

    @Override
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        orderLineRepository.save(orderLineMapper.toOrderLine(orderLineRequest)).getId();
    }
}
