package com.microservice.payment.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
