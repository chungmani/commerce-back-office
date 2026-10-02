package com.example.commercebackoffice.domain.order.dto;

public record CustomerOrderSummary(
        Long customerId,
        long totalOrderCount,
        long totalOrderPrice
) {
}
