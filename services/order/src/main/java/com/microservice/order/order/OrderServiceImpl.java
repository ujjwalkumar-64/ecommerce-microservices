package com.microservice.order.order;

import com.microservice.order.customer.CustomerClient;
import com.microservice.order.exception.BusinessException;
import com.microservice.order.kafka.OrderConfirmation;
import com.microservice.order.kafka.OrderProducer;
import com.microservice.order.orderline.OrderLineRequest;
import com.microservice.order.orderline.OrderLineService;
import com.microservice.order.product.ProductClient;
import com.microservice.order.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderLineService orderLineService;

    @Autowired
    private OrderProducer orderProducer;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // step 1: check customer -- openfeign use
        var customer= customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(()-> new BusinessException("cannot create order :: customer not found with id :: " + orderRequest.customerId()));

        // step 2: purchase the product -- restTemplate use
         var purchaseProducts = productClient.purchaseProducts(orderRequest.products());

        // step 3: order persist
        var order = orderRepository.save(orderMapper.toOrder(orderRequest));

        // step 4: orderLine persist
        for (PurchaseRequest purchaseRequest : orderRequest.products() ){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // step 5: start payment process
        // todo setup payment

        // step 6: send order confirmation to kafka ms -- kafka use
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.totalAmount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );
        return new OrderResponse(
                order.getId(),
                orderRequest.reference(),
                order.getTotalAmount(),
                orderRequest.paymentMethod(),
                customer.id()
        );
    }

    @Override
    public List<OrderResponse> findAllOrders(){
         return orderRepository.findAll()
                 .stream()
                 .map(orderMapper ::fromOrder)
                 .toList();

    }

    @Override
    public OrderResponse findOrderById(Integer orderId) {
         return orderRepository.findById(orderId)
                 .map(orderMapper::fromOrder)
                 .orElseThrow(()-> new EntityNotFoundException("order not found with id :: " + orderId));

    }

}
