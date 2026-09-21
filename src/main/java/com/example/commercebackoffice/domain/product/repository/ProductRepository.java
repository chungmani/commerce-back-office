package com.example.commercebackoffice.domain.product.repository;

import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
    SELECT p FROM Product p
        WHERE (:keyword IS NULL OR p.name LIKE CONCAT('%', :keyword, '%')) AND
             (:category IS NULL OR p.category = :category) AND (:state IS NULL OR p.state = :state)
    """)
    Page<Product> findAllByKeywordAndFilter(
            @Param("keyword") String keyword, @Param("category") String category,
            @Param("state") ProductState state, Pageable pageable);
}
