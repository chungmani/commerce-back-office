package com.example.commercebackoffice.domain.product.dto;

import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;
import com.example.commercebackoffice.domain.review.dto.LatestReviewResponse;
import com.example.commercebackoffice.domain.review.dto.ReviewSummaryResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetProductResponse(
        String name,
        String category,
        int price,
        int stock,
        ProductState state,
        LocalDateTime createdAt,
        String adminName,
        String adminEmail,
        ReviewSummaryResponse summaryResponse,
        List<LatestReviewResponse> latestReviewResponses
) {
    public static GetProductResponse from(Product product,
                                          ReviewSummaryResponse summaryResponse,
                                          List<LatestReviewResponse> latestReviewResponses) {
        return new GetProductResponse(
                product.getName(), product.getCategory(),
                product.getPrice(), product.getStock(),
                product.getState(), product.getCreatedAt(),
                product.getAdmin().getName(), product.getAdmin().getEmail(),
                summaryResponse, latestReviewResponses
        );
    }
}
