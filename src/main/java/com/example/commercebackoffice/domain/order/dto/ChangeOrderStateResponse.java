package com.example.commercebackoffice.domain.order.dto;

import com.example.commercebackoffice.domain.order.entity.Order;
import com.example.commercebackoffice.domain.order.enums.OrderState;

public record ChangeOrderStateResponse(
        Long id,
        OrderState state
) {
    public static ChangeOrderStateResponse from(Order order) {
        return new ChangeOrderStateResponse(
                order.getId(), order.getState()
        );
    }
}
