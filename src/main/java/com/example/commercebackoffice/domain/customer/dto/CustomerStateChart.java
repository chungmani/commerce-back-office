package com.example.commercebackoffice.domain.customer.dto;

public record CustomerStateChart(
        long activeCustomer,
        long inactiveCustomer,
        long suspendCustomer
) {
}
