package com.example.commercebackoffice.domain.order.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.auth.dto.SessionAdmin;
import com.example.commercebackoffice.domain.order.dto.CreateOrderRequest;
import com.example.commercebackoffice.domain.order.dto.CreateOrderResponse;
import com.example.commercebackoffice.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<ApiResponse<CreateOrderResponse>> create(
            @Valid @RequestBody CreateOrderRequest request,
            @SessionAttribute(name = "loginAdmin")SessionAdmin sessionAdmin
            ) {
        Long adminId = sessionAdmin.id();
        return ResponseEntity.status(ResponseCode.CREATED.getStatus())
                .body(ApiResponse.success(ResponseCode.CREATED, orderService.create(request, adminId)));
    }

}
