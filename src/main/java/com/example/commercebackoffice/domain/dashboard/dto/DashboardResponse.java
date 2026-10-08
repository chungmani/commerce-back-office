package com.example.commercebackoffice.domain.dashboard.dto;

import java.util.List;

public record DashboardResponse(
        SummaryStatistics summaryStatistics,
        WidgetsData widgetsData,
        ChartsData chartsData,
        List<LatestOrderList> latestOrderList
) {
}
