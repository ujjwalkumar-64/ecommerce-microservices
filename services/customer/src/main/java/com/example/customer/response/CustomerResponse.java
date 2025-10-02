package com.example.customer.response;

import com.example.customer.entity.Address;

public record CustomerResponse (
      String id,
      String firstName,
      String lastName,
      String email,
      Address address
){}
