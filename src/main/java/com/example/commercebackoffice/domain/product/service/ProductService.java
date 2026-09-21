package com.example.commercebackoffice.domain.product.service;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import com.example.commercebackoffice.domain.product.dto.*;
import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;
import com.example.commercebackoffice.domain.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminService adminService;

    // 상품 등록
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

    // 상품 전체 조회
    public Page<GetProductsResponse> findAll(String keyword, String category, ProductState state, Pageable pageable) {
        if (pageable.getPageNumber() < 1) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());

        Page<Product> products = productRepository.findAllByKeywordAndFilter(keyword, category, state, pageable);

        return products.map(GetProductsResponse::from);
    }

    // 상품 상세 조회
    public GetProductResponse findOne(Long productId) {
        Product product = getProductById(productId);
        return GetProductResponse.from(product);
    }

    // 상품 정보 수정
    @Transactional
    public UpdateProductResponse update(Long productId, UpdateProductRequest request) {
        Product product = getProductById(productId);
        product.updateProduct(request.name(), request.category(), request.price());
        return UpdateProductResponse.from(product);
    }

    // 상품 재고 변경 메서드
    @Transactional
    public void changeStock(Long productId, int quantity) {
        Product product = getProductById(productId);

        if (product.getStock() < quantity) {
            throw new BusinessException(ResponseCode.PRODUCT_INSUFFICIENT_STOCK);
        }

        product.decreaseStock(quantity);

        if (product.getState().equals(ProductState.DISCONTINUED)) {
            return;
        }
        if (product.getStock() == 0) {
            product.changeProductState(ProductState.SOLD_OUT);
        }
        if (product.getStock() >= 1) {
            product.changeProductState(ProductState.ON_SALE);
        }
    }

    // 상품 상태 변경
    @Transactional
    public ChangeProductStateResponse change(Long productId, ChangeProductStateRequest request) {
        Product product = getProductById(productId);
        product.changeProductState(request.state());
        return ChangeProductStateResponse.from(product);
    }


    // 상품 조회 공통 메서드
    private Product getProductById (Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new BusinessException(ResponseCode.PRODUCT_NOT_FOUND)
        );
    }


}
