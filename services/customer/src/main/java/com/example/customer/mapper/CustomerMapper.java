package com.example.customer.mapper;

import com.example.customer.entity.Customer;
import com.example.customer.request.CustomerRequest;
import com.example.customer.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest customerRequest) {
        if(customerRequest == null) return null;
        return Customer.builder()
                .id(customerRequest.id())
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .address(customerRequest.address())

                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        if(customer == null) return null;
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
