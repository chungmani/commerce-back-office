package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;

public record GetProfileResponse(
        String name,
        String email,
        String phoneNumber
) {
    public static GetProfileResponse from(Admin admin) {
        return new GetProfileResponse(
                admin.getName(), admin.getEmail(), admin.getPhoneNumber()
        );
    }
}
