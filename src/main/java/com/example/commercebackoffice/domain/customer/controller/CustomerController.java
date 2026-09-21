package com.example.commercebackoffice.domain.customer.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.customer.dto.*;
import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import com.example.commercebackoffice.domain.customer.service.CustomerService;
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
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    // 고객 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<GetCustomersResponse>>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerState state,
            @PageableDefault(page = 1, size = 10, sort = "name", direction = Sort.Direction.ASC)Pageable pageable
            ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, customerService.findAll(keyword, state, pageable)));
    }

    // 고객 상세 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<GetCustomerResponse>> getOne(
            @PathVariable Long customerId
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, customerService.findOne(customerId)));
    }

    // 고객 정보 수정
    @PatchMapping("/{customerId}")
    public ResponseEntity<ApiResponse<UpdateCustomerResponse>> update(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, customerService.update(customerId, request)));
    }

    // 고객 상태 변경
    @PatchMapping("/{customerId}/state")
    public ResponseEntity<ApiResponse<ChangeCustomerStateResponse>> changeState(
            @PathVariable Long customerId,
            @Valid @RequestBody ChangeCustomerStateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, customerService.changeState(customerId, request)));
    }
}
