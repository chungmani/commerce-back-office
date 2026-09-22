package com.example.commercebackoffice.domain.order.dto;

import com.example.commercebackoffice.domain.order.enums.OrderState;
import jakarta.validation.constraints.NotNull;

public record ChangeOrderStateRequest(
        @NotNull(message = "주문 상태를 입력해주세요.")
        OrderState state
) {
}
