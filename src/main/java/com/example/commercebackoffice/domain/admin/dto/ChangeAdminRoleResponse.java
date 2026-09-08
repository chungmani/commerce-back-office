package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;

public record ChangeAdminRoleResponse(
        AdminRole role
) {
    public static ChangeAdminRoleResponse from(Admin admin) {
        return new ChangeAdminRoleResponse(admin.getRole());
    }
}
