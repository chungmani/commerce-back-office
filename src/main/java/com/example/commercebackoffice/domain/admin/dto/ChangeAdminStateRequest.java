package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.enums.AdminState;
import jakarta.validation.constraints.NotNull;

public record ChangeAdminStateRequest(
        @NotNull
        AdminState state
) {
}
