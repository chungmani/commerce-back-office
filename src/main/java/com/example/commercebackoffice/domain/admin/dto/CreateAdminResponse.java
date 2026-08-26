package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;

import java.time.LocalDateTime;

public record CreateAdminResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        AdminRole role,
        LocalDateTime createdAt
) {
    public static CreateAdminResponse from(Admin admin) {
        return new CreateAdminResponse(
                admin.getId(), admin.getName(),
                admin.getEmail(), admin.getPhoneNumber(),
                admin.getRole(), admin.getCreatedAt()
        );
    }
}
