package com.example.commercebackoffice.domain.admin.enums;

import lombok.Getter;

@Getter
public enum AdminState {
    ACTIVE(""),
    INACTIVE("비활성화된 계정입니다."),
    SUSPENDED("정지된 계정입니다."),
    PENDING("승인대기중인 계정입니다."),
    DENIED("거부된 계정입니다.");

    String message;
    AdminState(String message) {
        this.message = message;
    }

    public boolean canLogin() {
        return this == ACTIVE;
    }
}
