package com.example.commercebackoffice.domain.customer.dto;

import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;

import java.time.LocalDateTime;

public record GetCustomersResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        CustomerState state,
        LocalDateTime createdAt
) {
    public static GetCustomersResponse from(Customer customer) {
        return new GetCustomersResponse(
                customer.getId(), customer.getName(),
                customer.getEmail(), customer.getPhoneNumber(),
                customer.getState(), customer.getCreatedAt()
        );
    }
}
