package com.example.commercebackoffice.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull(message = "고객 ID를 입력해주세요.")
        Long customerId,

        @NotNull(message = "상품 ID를 입력해주세요.")
        Long productId,

        @Min(value = 1, message = "주문 수량은 1개 이상이어야 합니다.")
        int quantity
) {
}
