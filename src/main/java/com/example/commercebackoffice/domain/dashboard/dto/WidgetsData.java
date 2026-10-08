package com.example.commercebackoffice.domain.dashboard.dto;

public record WidgetsData(
        long orderPrice,
        long todayOrderPrice,
        long preparingOrder,
        long shippingOrder,
        long deliveredOrder,
        long lowStock,
        long soldOutProduct
) {
}
