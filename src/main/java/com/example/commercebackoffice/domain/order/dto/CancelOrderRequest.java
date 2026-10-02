package com.example.commercebackoffice.domain.order.dto;

import jakarta.validation.constraints.NotBlank;

public record CancelOrderRequest(
        @NotBlank(message = "취소 사유를 적어주세요.")
        String cancelReason
) {
}
