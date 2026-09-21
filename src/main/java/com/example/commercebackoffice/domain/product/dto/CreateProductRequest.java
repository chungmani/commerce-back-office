package com.example.commercebackoffice.domain.product.dto;

import com.example.commercebackoffice.domain.product.enums.ProductState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(

        @NotBlank(message = "상품명을 입력하세요.")
        String name,

        @NotBlank(message = "카테고리명을 입력하세요.")
        String category,

        @Min(0)
        int price,

        @Min(0)
        int stock,

        @NotNull(message = "상품 상태를 입력하세요.")
        ProductState state
) {
}
