package com.example.commercebackoffice.domain.dashboard.dto;

public record SummaryStatistics(
        long totalAdmin,
        long activeAdmin,
        long totalCustomer,
        long activeCustomer,
        long totalProduct,
        long lowStock,
        long totalOrder,
        long todayOrder,
        long totalReview,
        double averageRating
) {
}
