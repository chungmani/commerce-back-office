package com.example.commercebackoffice.domain.product.enums;

import lombok.Getter;

@Getter
public enum ProductState {
    ON_SALE("판매중입니다."),
    SOLD_OUT("품절입니다."),
    DISCONTINUED("단종되었습니다.");

    private String message;

    ProductState(String message) {
        this.message = message;
    }
}
