package com.example.commercebackoffice.domain.order.dto;

import com.example.commercebackoffice.domain.order.entity.Order;
import com.example.commercebackoffice.domain.order.enums.OrderState;

import java.time.LocalDateTime;

public record GetOrdersResponse(
        Long id,
        String orderNumber,
        String customerName,
        String productName,
        int quantity,
        int productPrice,
        int totalPrice,
        LocalDateTime createdAt,
        OrderState state,
        String adminName
) {
    public static GetOrdersResponse from(Order order) {
        return new GetOrdersResponse(
                order.getId(), order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getProductName(),
                order.getQuantity(),
                order.getProductPrice(),
                order.getProductPrice() * order.getQuantity(),
                order.getCreatedAt(),
                order.getState(),
                order.getAdmin() != null ? order.getAdmin().getName() : null
        );
    }
}
