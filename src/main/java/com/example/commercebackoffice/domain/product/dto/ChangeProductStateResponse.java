package com.example.commercebackoffice.domain.product.dto;

import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;

public record ChangeProductStateResponse(
        Long id,
        ProductState state
) {
    public static ChangeProductStateResponse from(Product product) {
        return new ChangeProductStateResponse(
                product.getId(), product.getState()
        );
    }
}
