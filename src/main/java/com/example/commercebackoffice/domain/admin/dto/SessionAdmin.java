package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;

public record SessionAdmin(
        Long id,
        String email,
        AdminRole role
) {
    public static SessionAdmin from(Admin admin) {
        return new SessionAdmin(
                admin.getId(), admin.getEmail(), admin.getRole()
        );
    }
}
