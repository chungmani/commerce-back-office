package com.example.commercebackoffice.domain.product.dto;

import com.example.commercebackoffice.domain.product.entity.Product;

public record UpdateProductResponse(
        Long id,
        String name,
        String category,
        int price
) {
    public static UpdateProductResponse from(Product product) {
        return new UpdateProductResponse(
                product.getId(), product.getName(),
                product.getCategory(), product.getPrice()
        );
    }
}
