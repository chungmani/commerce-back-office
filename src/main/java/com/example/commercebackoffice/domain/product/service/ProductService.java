package com.example.commercebackoffice.domain.product.service;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import com.example.commercebackoffice.domain.product.dto.CreateProductRequest;
import com.example.commercebackoffice.domain.product.dto.CreateProductResponse;
import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminService adminService;

    @Transactional
    public CreateProductResponse create(CreateProductRequest request, Long adminId) {
        Admin admin = adminService.getOperationAdmin(adminId);

        Product product = new Product(
                request.name(), request.category(), request.stock(),
                request.price(), request.state(), admin
        );

        Product savedProduct = productRepository.save(product);

        return CreateProductResponse.from(savedProduct);
    }
}
