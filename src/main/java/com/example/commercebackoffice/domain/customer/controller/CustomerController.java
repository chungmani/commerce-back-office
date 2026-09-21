package com.example.commercebackoffice.domain.customer.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.customer.dto.GetCustomersResponse;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import com.example.commercebackoffice.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<GetCustomersResponse>>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerState state,
            @PageableDefault(page = 1, size = 10, sort = "name", direction = Sort.Direction.ASC)Pageable pageable
            ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, customerService.findAll(keyword, state, pageable)));
    }
}
