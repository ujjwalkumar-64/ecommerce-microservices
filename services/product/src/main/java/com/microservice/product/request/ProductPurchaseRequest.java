package com.microservice.product.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id is required")
        Integer productId,
        @Positive(message = "Product quantity must be positive")
        double quantity
) {
}
