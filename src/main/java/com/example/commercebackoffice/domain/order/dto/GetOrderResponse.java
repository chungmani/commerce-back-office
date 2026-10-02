package com.example.commercebackoffice.domain.order.dto;

import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.order.entity.Order;
import com.example.commercebackoffice.domain.order.enums.OrderState;

import java.time.LocalDateTime;

public record GetOrderResponse(
        String orderNumber,
        String customerName,
        String customerEmail,
        String productName,
        int quantity,
        int productPrice,
        int totalPrice,
        LocalDateTime createdAt,
        OrderState state,
        String adminName,
        String adminEmail,
        AdminRole role
) {
    public static GetOrderResponse from(Order order) {
        return new GetOrderResponse(
                order.getOrderNumber(), order.getCustomer().getName(),
                order.getCustomer().getEmail(), order.getProductName(),
                order.getQuantity(), order.getProductPrice(),
                order.getProductPrice() * order.getQuantity(),
                order.getCreatedAt(), order.getState(),
                order.getAdmin() != null ? order.getAdmin().getName() : null,
                order.getAdmin() != null ? order.getAdmin().getEmail() : null,
                order.getAdmin() != null ? order.getAdmin().getRole() : null
        );
    }
}
