package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminState;

public record ChangeAdminStateResponse(
        AdminState state
) {
    public static ChangeAdminStateResponse from(Admin admin) {
        return new ChangeAdminStateResponse(admin.getState());
    }
}
