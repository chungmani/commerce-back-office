package com.example.commercebackoffice.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record RejectAdminRequest(
        @NotBlank(message = "거부 사유를 입력해주세요.")
        String reason
) {
}
