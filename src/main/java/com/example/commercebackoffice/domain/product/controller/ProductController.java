package com.example.commercebackoffice.domain.product.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.auth.dto.SessionAdmin;
import com.example.commercebackoffice.domain.product.dto.CreateProductRequest;
import com.example.commercebackoffice.domain.product.dto.CreateProductResponse;
import com.example.commercebackoffice.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateProductResponse>> create(
            @Valid @RequestBody CreateProductRequest request,
            @SessionAttribute(name = "loginAdmin")SessionAdmin sessionAdmin
            ) {
        Long adminId = sessionAdmin.id();
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.CREATED, productService.create(request, adminId)));
    }
}
