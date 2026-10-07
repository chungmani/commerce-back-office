package com.example.commercebackoffice.domain.review.dto;

import com.example.commercebackoffice.domain.review.entity.Review;

import java.time.LocalDateTime;

public record GetReviewsResponse(
        Long id,
        String orderNumber,
        String customerName,
        String productName,
        int rating,
        String content,
        LocalDateTime createdAt
) {
    public static GetReviewsResponse from(Review review) {
        return new GetReviewsResponse(
                review.getId(), review.getOrder().getOrderNumber(),
                review.getOrder().getCustomer().getName(),
                review.getOrder().getProductName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
