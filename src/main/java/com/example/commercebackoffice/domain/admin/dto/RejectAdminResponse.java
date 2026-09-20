package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminState;

import java.time.LocalDateTime;

public record RejectAdminResponse(
        Long adminId,
        AdminState state,
        LocalDateTime deniedAt,
        String reason
) {
    public static RejectAdminResponse from(Admin admin) {
        return new RejectAdminResponse(
                admin.getId(), admin.getState(), admin.getDeniedAt(), admin.getDeniedReason()
        );
    }
}
