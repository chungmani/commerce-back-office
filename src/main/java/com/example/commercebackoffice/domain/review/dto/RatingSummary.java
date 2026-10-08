package com.example.commercebackoffice.domain.review.dto;

public record RatingSummary(
        long rating5,
        long rating4,
        long rating3,
        long rating2,
        long rating1
) {
}
