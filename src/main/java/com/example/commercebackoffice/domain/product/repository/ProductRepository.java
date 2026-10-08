package com.example.commercebackoffice.domain.product.repository;

import com.example.commercebackoffice.domain.product.dto.CategoryProductCount;
import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
    SELECT p FROM Product p
        WHERE (:keyword IS NULL OR p.name LIKE CONCAT('%', :keyword, '%')) AND
             (:category IS NULL OR p.category = :category) AND (:state IS NULL OR p.state = :state)
    """)
    Page<Product> findAllByKeywordAndFilter(
            @Param("keyword") String keyword, @Param("category") String category,
            @Param("state") ProductState state, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p")
    long countAll();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock <= 5")
    long lowStockCount();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.state = ProductState.SOLD_OUT")
    long soldOutProduct();

    @Query("""
    SELECT new com.example.commercebackoffice.domain.product.dto.CategoryProductCount(p.category, COUNT(p))
    FROM Product p GROUP BY p.category
    """)
    List<CategoryProductCount> countCategoryProduct();
}
