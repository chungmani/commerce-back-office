package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.enums.AdminState;

import java.time.LocalDateTime;

public record GetAdminResponse(
        String name,
        String email,
        String phoneNumber,
        AdminRole role,
        AdminState state,
        LocalDateTime createdAt,
        LocalDateTime approvedAt
) {
    public static GetAdminResponse from(Admin admin) {
        return new GetAdminResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getState(),
                admin.getCreatedAt(),
                admin.getApprovedAt()
        );
    }
}
