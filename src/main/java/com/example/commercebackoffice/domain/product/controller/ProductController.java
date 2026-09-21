package com.example.commercebackoffice.domain.product.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.auth.dto.SessionAdmin;
import com.example.commercebackoffice.domain.product.dto.CreateProductRequest;
import com.example.commercebackoffice.domain.product.dto.CreateProductResponse;
import com.example.commercebackoffice.domain.product.dto.GetProductsResponse;
import com.example.commercebackoffice.domain.product.enums.ProductState;
import com.example.commercebackoffice.domain.product.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping
    public ResponseEntity<ApiResponse<CreateProductResponse>> create(
            @Valid @RequestBody CreateProductRequest request,
            @SessionAttribute(name = "loginAdmin")SessionAdmin sessionAdmin
            ) {
        Long adminId = sessionAdmin.id();
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.CREATED, productService.create(request, adminId)));
    }

    // 상품 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<GetProductsResponse>>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) ProductState state,
            @PageableDefault(page = 1, size = 10, sort = "price", direction = Sort.Direction.ASC)Pageable pageable
            ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, productService.findAll(keyword, category, state, pageable)));
    }
}
