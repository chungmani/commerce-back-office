package com.example.commercebackoffice.domain.review.dto;

import com.example.commercebackoffice.domain.review.entity.Review;

import java.time.LocalDateTime;

public record LatestReviewResponse(
        String customerName,
        int rating,
        String content,
        LocalDateTime createdAt
) {
    public static LatestReviewResponse from(Review review) {
        return new LatestReviewResponse(
                review.getOrder().getCustomer().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
