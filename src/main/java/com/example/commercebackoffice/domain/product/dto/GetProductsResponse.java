package com.example.commercebackoffice.domain.product.dto;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;

import java.time.LocalDateTime;

public record GetProductsResponse(
        Long id,
        String name,
        String category,
        int price,
        int stock,
        ProductState state,
        LocalDateTime createdAt,
        String adminName
) {
    public static GetProductsResponse from(Product product) {
        return new GetProductsResponse(
                product.getId(), product.getName(),
                product.getCategory(), product.getPrice(),
                product.getStock(), product.getState(),
                product.getCreatedAt(), product.getAdmin().getName()
        );

    }
}
