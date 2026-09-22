package com.example.commercebackoffice.domain.order.dto;

import com.example.commercebackoffice.domain.order.entity.Order;
import com.example.commercebackoffice.domain.order.enums.OrderState;

public record CancelOrderResponse(
        Long id,
        OrderState state
) {
    public static CancelOrderResponse from(Order order) {
        return new CancelOrderResponse(
                order.getId(), order.getState()
        );
    }
}
