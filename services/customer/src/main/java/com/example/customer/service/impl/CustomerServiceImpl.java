package com.example.customer.service.impl;

import com.example.customer.entity.Customer;
import com.example.customer.exception.CustomerNotFoundException;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.request.CustomerRequest;
import com.example.customer.response.CustomerResponse;
import com.example.customer.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private  CustomerRepository customerRepository;

    @Autowired
    private  CustomerMapper customerMapper;

    private void mergeCustomer(Customer customer, CustomerRequest customerRequest) {

        if(StringUtils.isNotBlank(customerRequest.firstName())){
            customer.setFirstName(customerRequest.firstName());
        }
        if(StringUtils.isNotBlank(customerRequest.lastName())){
            customer.setLastName(customerRequest.lastName());
        }
        if(StringUtils.isNotBlank(customerRequest.email())){
            customer.setEmail(customerRequest.email());
        }
        if(customerRequest.address() != null){
            customer.setAddress(customerRequest.address());
        }

    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        var newCustomer = customerRepository.save(customerMapper.toCustomer( customerRequest ));
        return customerMapper.fromCustomer(newCustomer);
    }

    @Override
    public void updateCustomer(CustomerRequest customerRequest) {
        var customer= customerRepository.findById(customerRequest.id()).orElseThrow(()-> new CustomerNotFoundException("cannot update customer :: invalid id :: " + customerRequest.id()));
        mergeCustomer( customer, customerRequest );
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponse>  findAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::fromCustomer).toList();

    }

    @Override
    public CustomerResponse findCustomerById(String id) {
        return customerRepository.findById(id).map(customerMapper::fromCustomer).orElseThrow(()-> new CustomerNotFoundException("cannot find customer :: " + id));
    }

    @Override
    public boolean existsCustomerById(String id) {
        return customerRepository.existsById(id);
    }

    @Override
    public void deleteCustomerById(String id){
        customerRepository.deleteById(id);
    }

}

