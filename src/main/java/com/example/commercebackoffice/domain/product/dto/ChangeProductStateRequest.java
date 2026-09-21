package com.example.commercebackoffice.domain.product.dto;

import com.example.commercebackoffice.domain.product.enums.ProductState;
import jakarta.validation.constraints.NotNull;

public record ChangeProductStateRequest(
        @NotNull(message = "상품 상태를 입력하세요.")
        ProductState state
) {
}
