package com.microservice.order.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record PurchaseRequest(
    @NotNull
    Integer productId,
    @Positive
    double quantity

) {
}
