package com.example.commercebackoffice.domain.admin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminRole {
    SUPER_ADMIN("ROLE_SUPER_ADMIN", "슈퍼관리자"),
    OPERATION_ADMIN("ROLE_OPERATION_ADMIN", "운영관리자"),
    CS_ADMIN("ROLE_CS_ADMIN", "CS 관리자");

    private final String role;
    private final String description;
}
