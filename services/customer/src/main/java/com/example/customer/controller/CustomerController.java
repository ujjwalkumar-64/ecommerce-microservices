package com.example.customer.controller;

import com.example.customer.request.CustomerRequest;
import com.example.customer.response.CustomerResponse;
import com.example.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")

public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping
    ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest){
          return  ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @PutMapping
    ResponseEntity<Void> updateCustomer(@RequestBody  @Valid CustomerRequest customerRequest){
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        return  ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    ResponseEntity<Boolean> existCustomerById(@PathVariable("customer-id") String id){
        return ResponseEntity.ok(customerService.existsCustomerById(id));
    }

    @GetMapping("/{customer-id}")
    ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("customer-id") String id){
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @DeleteMapping("/{customer-id}")
    ResponseEntity<Void> deleteCustomer(@PathVariable("customer-id") String id){
        customerService.deleteCustomerById(id);
        return  ResponseEntity.accepted().build();
    }

}
