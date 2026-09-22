package com.example.commercebackoffice.domain.order.enums;

import lombok.Getter;

@Getter
public enum OrderState {
    PREPARING("준비중"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    CANCELED("취소");

    String message;

    OrderState(String message) {
        this.message = message;
    }

}
