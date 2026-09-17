package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.enums.AdminState;

public record ChangeAdminStateRequest(
        AdminState state
) {
}
