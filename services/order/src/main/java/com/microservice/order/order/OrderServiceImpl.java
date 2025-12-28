package com.microservice.order.order;

import com.microservice.order.customer.CustomerClient;
import com.microservice.order.customer.CustomerResponse;
import com.microservice.order.exception.BusinessException;
import com.microservice.order.kafka.OrderConfirmation;
import com.microservice.order.kafka.OrderProducer;
import com.microservice.order.kafka.PaymentProducer;
import com.microservice.order.orderline.OrderLineRequest;
import com.microservice.order.orderline.OrderLineService;
import com.microservice.order.payment.PaymentClient;
import com.microservice.order.payment.PaymentRequest;
import com.microservice.order.product.ProductClient;
import com.microservice.order.product.PurchaseRequest;
import com.microservice.order.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @Autowired
    private PaymentProducer paymentProducer;

    @Autowired
    private PaymentClient paymentClient;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // step 1: check customer -- openfeign use -- and parallel way
        CompletableFuture<CustomerResponse> customerFuture= CompletableFuture.supplyAsync(
                ()-> customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(()-> new BusinessException("cannot create order :: customer not found with id :: " + orderRequest.customerId()))
        );

        // step 2: purchase the product -- restTemplate use  --- and parallel way
        CompletableFuture<List<PurchaseResponse>> purchaseProductsFuture = CompletableFuture.supplyAsync(
                ()-> productClient.purchaseProducts(orderRequest.products())
        );

        // wait for both
        CompletableFuture.allOf(customerFuture, purchaseProductsFuture).join();

        var customer= customerFuture.join();
        var purchaseProducts= purchaseProductsFuture.join();

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
        paymentProducer.sendPaymentRequest(
                new PaymentRequest(
                        order.getId(),
                        orderRequest.totalAmount(),
                        order.getPaymentMethod(),
                        customer,
                        order.getReference()
                )
        );


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
