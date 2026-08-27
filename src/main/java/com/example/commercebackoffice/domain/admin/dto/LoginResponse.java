package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;

public record LoginResponse(
        Long id,
        String email
) {
    public static LoginResponse from(Admin admin) {
        return new LoginResponse(
                admin.getId(),
                admin.getEmail()
        );
    }
}
