package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminState;

import java.time.LocalDateTime;

public record ApproveAdminResponse(
        Long adminId,
        AdminState state,
        LocalDateTime approvedAt
) {
    public static ApproveAdminResponse from(Admin admin) {
        return new ApproveAdminResponse(
                admin.getId(), admin.getState(), admin.getApprovedAt()
        );
    }
}
