package com.example.commercebackoffice.domain.review.dto;

import com.example.commercebackoffice.domain.review.entity.Review;

import java.time.LocalDateTime;

public record GetReviewResponse(
        String productName,
        String customerName,
        String customerEmail,
        LocalDateTime createdAt,
        int rating,
        String content
) {
    public static GetReviewResponse from(Review review) {
        return new GetReviewResponse(
                review.getOrder().getProductName(),
                review.getOrder().getCustomer().getName(),
                review.getOrder().getCustomer().getEmail(),
                review.getCreatedAt(),
                review.getRating(),
                review.getContent()
        );
    }
}
