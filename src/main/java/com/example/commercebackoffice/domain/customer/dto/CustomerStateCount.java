package com.example.commercebackoffice.domain.customer.dto;

import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerStateCount {

    private CustomerState state;
    private long count;
}
