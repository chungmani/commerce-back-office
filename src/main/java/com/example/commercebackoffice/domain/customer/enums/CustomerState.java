package com.example.commercebackoffice.domain.customer.enums;

import lombok.Getter;

@Getter
public enum CustomerState {
    ACTIVE(""),
    INACTIVE("비활성화된 계정입니다."),
    SUSPENDED("정지된 계정입니다.");

    String message;
    CustomerState(String message) {
        this.message = message;
    }

}
