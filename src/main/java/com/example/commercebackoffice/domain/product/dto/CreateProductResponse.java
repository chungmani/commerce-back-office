package com.example.commercebackoffice.domain.product.dto;

import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;

public record CreateProductResponse(
        Long id,
        String name,
        String category,
        int price,
        int stock,
        ProductState state
) {
    public static CreateProductResponse from(Product product) {
        return new CreateProductResponse(
                product.getId(), product.getName(),
                product.getCategory(), product.getPrice(),
                product.getStock(), product.getState()
        );
    }
}
