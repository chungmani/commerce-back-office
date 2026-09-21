package com.example.commercebackoffice.domain.customer.dto;

import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;

import java.time.LocalDateTime;

public record GetCustomerResponse(
        String name,
        String email,
        String phoneNumber,
        CustomerState state,
        LocalDateTime createdAt
) {
    public static GetCustomerResponse from(Customer customer) {
        return new GetCustomerResponse(
                customer.getName(), customer.getEmail(),
                customer.getPhoneNumber(), customer.getState(),
                customer.getCreatedAt()
        );
    }
}
