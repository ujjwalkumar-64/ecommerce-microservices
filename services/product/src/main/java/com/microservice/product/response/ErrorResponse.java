package com.microservice.product.response;


import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
