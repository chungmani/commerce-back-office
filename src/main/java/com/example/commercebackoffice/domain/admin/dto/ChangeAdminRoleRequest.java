package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import jakarta.validation.constraints.NotNull;

public record ChangeAdminRoleRequest(
        @NotNull(message = "관리자 역할을 입력해주세요.")
        AdminRole role
) {
}
