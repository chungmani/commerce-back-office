package com.example.commercebackoffice.domain.product.repository;

import com.example.commercebackoffice.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
