package com.example.commercebackoffice.domain.product.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.auth.dto.SessionAdmin;
import com.example.commercebackoffice.domain.product.dto.*;
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
        return ResponseEntity.status(ResponseCode.CREATED.getStatus())
                .body(ApiResponse.success(ResponseCode.CREATED, productService.create(request, adminId)));
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

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<GetProductResponse>> getOne(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, productService.findOne(productId)));
    }

    // 상품 정보 수정
    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<UpdateProductResponse>> update(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, productService.update(productId, request)));
    }

    // 상품 상태 변경
    @PatchMapping("/{productId}/state")
    public ResponseEntity<ApiResponse<ChangeProductStateResponse>> change(
            @PathVariable Long productId,
            @Valid @RequestBody ChangeProductStateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, productService.change(productId, request)));
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

}
