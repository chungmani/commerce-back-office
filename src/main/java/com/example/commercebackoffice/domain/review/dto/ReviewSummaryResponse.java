package com.example.commercebackoffice.domain.review.dto;

public record ReviewSummaryResponse(
        double ratingAverage,
        long reviewCount,
        RatingSummary ratingSummary
) {
    public static ReviewSummaryResponse from(
            double ratingAverage, long reviewCount, RatingSummary ratingSummary) {
        return new ReviewSummaryResponse(
                ratingAverage, reviewCount, ratingSummary
        );
    }
}
