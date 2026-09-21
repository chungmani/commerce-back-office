package com.example.commercebackoffice.domain.customer.dto;

import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;

public record ChangeCustomerStateResponse(
        Long id,
        CustomerState state
) {
    public static ChangeCustomerStateResponse from(Customer customer) {
        return new ChangeCustomerStateResponse(
                customer.getId(), customer.getState()
        );
    }
}
