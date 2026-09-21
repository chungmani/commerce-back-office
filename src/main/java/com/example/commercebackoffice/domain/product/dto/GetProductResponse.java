package com.example.commercebackoffice.domain.product.dto;

import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;

import java.time.LocalDateTime;

public record GetProductResponse(
        String name,
        String category,
        int price,
        int stock,
        ProductState state,
        LocalDateTime createdAt,
        String adminName,
        String adminEmail
) {
    public static GetProductResponse from(Product product) {
        return new GetProductResponse(
                product.getName(), product.getCategory(),
                product.getPrice(), product.getStock(),
                product.getState(), product.getCreatedAt(),
                product.getAdmin().getName(), product.getAdmin().getEmail()
        );
    }
}
