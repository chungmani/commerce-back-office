package com.example.commercebackoffice.domain.product.entity;

import com.example.commercebackoffice.common.entity.BaseEntity;
import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.product.enums.ProductState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private ProductState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Product(String name, String category, int stock, int price, ProductState state, Admin admin) {
        if (stock < 0) {
            throw new BusinessException(ResponseCode.PRODUCT_INVALID_STOCK);
        }
        if (price < 0) {
            throw new BusinessException(ResponseCode.PRODUCT_INVALID_PRICE);
        }
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
        this.state = state;
        this.admin = admin;
    }

    public void updateProduct(String name, String category, int price) {
        if (price < 0) {
            throw new BusinessException(ResponseCode.PRODUCT_INVALID_PRICE);
        }

        this.name = name;
        this.category = category;
        this.price = price;
    }
}
