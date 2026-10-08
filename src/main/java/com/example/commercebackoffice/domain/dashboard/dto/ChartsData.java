package com.example.commercebackoffice.domain.dashboard.dto;

import com.example.commercebackoffice.domain.customer.dto.CustomerStateChart;
import com.example.commercebackoffice.domain.product.dto.CategoryProductCount;
import com.example.commercebackoffice.domain.review.dto.RatingSummary;

import java.util.List;

public record ChartsData(
        RatingSummary ratingSummary,
        CustomerStateChart customerStateChart,
        List<CategoryProductCount> categoryProductCounts
) {
}
