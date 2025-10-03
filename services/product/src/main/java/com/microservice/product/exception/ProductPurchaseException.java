package com.microservice.product.exception;

public class ProductPurchaseException extends RuntimeException {
    public ProductPurchaseException(String message) {
        super(message);
    }
}
