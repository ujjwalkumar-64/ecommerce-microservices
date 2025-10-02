package com.example.customer.service;

import com.example.customer.request.CustomerRequest;
import com.example.customer.response.CustomerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService  {
      CustomerResponse createCustomer(CustomerRequest customerRequest);
      void updateCustomer(CustomerRequest customerRequest);
      List<CustomerResponse> findAllCustomers();
      CustomerResponse findCustomerById(String id);
      boolean existsCustomerById(String id);
      void deleteCustomerById(String id);
}
