package com.example.commercebackoffice.domain.auth.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;

public record LoginResponse(
        Long id,
        String email,
        String token
) {
    public static LoginResponse from(Admin admin, String token) {
        return new LoginResponse(
                admin.getId(),
                admin.getEmail(),
                token
        );
    }
}
