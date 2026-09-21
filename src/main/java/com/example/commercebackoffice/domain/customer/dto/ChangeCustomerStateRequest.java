package com.example.commercebackoffice.domain.customer.dto;

import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerStateRequest(
        @NotNull
        CustomerState state
) {
}
