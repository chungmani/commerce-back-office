package com.example.commercebackoffice.common.security;

import com.example.commercebackoffice.domain.admin.enums.AdminRole;

public record AdminPrincipal(
        Long adminId,
        String email,
        AdminRole role
) {
}
