package com.microservice.order.orderline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private OrderLineMapper orderLineMapper;

    @Override
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        return orderLineRepository.save(orderLineMapper.toOrderLine(orderLineRequest)).getId();
    }

    @Override
    public List<OrderLineResponse> findAllByOrderId(Integer orderId){
         return orderLineRepository.findAllByOrderId(orderId)
                 .stream()
                 .map(orderLineMapper::fromOrderLine)
                 .toList();

    }
}
