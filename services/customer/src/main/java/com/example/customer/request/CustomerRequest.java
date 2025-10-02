package com.example.customer.request;

import com.example.customer.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest  (
      String id,

      @NotNull(message = "customer first name required")
      String firstName,
      @NotNull(message = "customer last name required")
      String lastName,
      @NotNull(message = "customer email required")
      @Email(message = "customer email is not valid")
      String email,
      Address address
){

}
