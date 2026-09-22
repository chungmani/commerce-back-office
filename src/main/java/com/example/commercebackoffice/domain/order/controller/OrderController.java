package com.example.commercebackoffice.domain.order.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.auth.dto.SessionAdmin;
import com.example.commercebackoffice.domain.order.dto.CreateOrderRequest;
import com.example.commercebackoffice.domain.order.dto.CreateOrderResponse;
import com.example.commercebackoffice.domain.order.dto.GetOrderResponse;
import com.example.commercebackoffice.domain.order.dto.GetOrdersResponse;
import com.example.commercebackoffice.domain.order.enums.OrderState;
import com.example.commercebackoffice.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // 주문 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<GetOrdersResponse>>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderState state,
            @PageableDefault(page = 1, size = 10, sort = "createdAt", direction = Sort.Direction.ASC)Pageable pageable
            ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, orderService.findAll(keyword, state, pageable)));
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<GetOrderResponse>> getOne(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, orderService.findOne(orderId)));
    }

}
