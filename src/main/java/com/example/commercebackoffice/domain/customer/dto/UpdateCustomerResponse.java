package com.example.commercebackoffice.domain.customer.dto;

import com.example.commercebackoffice.domain.customer.entity.Customer;

public record UpdateCustomerResponse(
        Long id,
        String name,
        String email,
        String phoneNumber
) {
    public static UpdateCustomerResponse from(Customer customer) {
        return new UpdateCustomerResponse(
                customer.getId(), customer.getName(),
                customer.getEmail(), customer.getPhoneNumber()
        );
    }
}
