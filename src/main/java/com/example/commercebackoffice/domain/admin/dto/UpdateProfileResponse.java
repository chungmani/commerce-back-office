package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;

public record UpdateProfileResponse(
        Long adminId,
        String name,
        String email,
        String phoneNumber
) {
    public static UpdateProfileResponse from(Admin admin) {
        return new UpdateProfileResponse(
                admin.getId(), admin.getName(), admin.getEmail(), admin.getPhoneNumber()
        );
    }
}
