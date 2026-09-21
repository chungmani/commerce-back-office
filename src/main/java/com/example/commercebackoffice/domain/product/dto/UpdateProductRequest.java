package com.example.commercebackoffice.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateProductRequest(

        @NotBlank(message = "상품명을 입력해주세요.")
        String name,

        @NotBlank(message = "카테고리를 입력해주세요.")
        String category,

        @Min(0)
        int price
) {
}
