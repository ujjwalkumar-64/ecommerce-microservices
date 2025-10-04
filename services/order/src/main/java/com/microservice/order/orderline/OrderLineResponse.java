package com.microservice.order.orderline;

import lombok.Builder;

@Builder
public record OrderLineResponse(
    Integer id,
    double quantity
) {
}
