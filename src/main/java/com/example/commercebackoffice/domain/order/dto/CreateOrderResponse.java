package com.example.commercebackoffice.domain.order.dto;

import com.example.commercebackoffice.domain.order.entity.Order;

import java.time.LocalDateTime;

public record CreateOrderResponse(
        String orderNumber,
        String productName,
        int productPrice,
        int quantity,
        int totalPrice,
        LocalDateTime createdAt
) {
    public static CreateOrderResponse from(Order order) {
        return new CreateOrderResponse(
                order.getOrderNumber(), order.getProductName(),
                order.getProductPrice(), order.getQuantity(),
                order.getProductPrice()*order.getQuantity(),
                order.getCreatedAt()
        );
    }
}
