package com.example.commercebackoffice.domain.dashboard.dto;

import com.example.commercebackoffice.domain.order.entity.Order;
import com.example.commercebackoffice.domain.order.enums.OrderState;

public record LatestOrderList(
        String orderNumber,
        String customerName,
        String productName,
        int totalPrice,
        OrderState orderState
) {
    public static LatestOrderList from(Order order) {
        return new LatestOrderList(
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getProductName(),
                order.getProductPrice() * order.getQuantity(),
                order.getState()
        );
    }
}
