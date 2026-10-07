package com.example.commercebackoffice.domain.review.repository;

import com.example.commercebackoffice.domain.review.dto.RatingCount;
import com.example.commercebackoffice.domain.review.dto.RatingSummary;
import com.example.commercebackoffice.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
    SELECT r FROM Review r
    WHERE (:keyword IS NULL OR r.order.customer.name LIKE CONCAT('%', :keyword, '%')
        OR r.order.productName LIKE CONCAT('%', :keyword, '%')) AND
             (:rating IS NULL OR r.rating = :rating)
    """)
    Page<Review> findAllByKeywordAndRating(@Param("keyowrd") String keyword, @Param("rating") Integer rating, Pageable pageable);

    @Query("""
    SELECT COALESCE(AVG(r.rating), 0.0) FROM Review r
    WHERE r.order.product.id = :productId
    """)
    double findByProduct_IdReviewAverage(@Param("productId") Long productId);

    @Query("""
    SELECT COUNT(r) FROM Review r
    WHERE r.order.product.id = :productId
    """)
    long findByProduct_IdReviewCount(@Param("productId") Long productId);

    @Query("""
    SELECT new com.example.commercebackoffice.domain.review.dto.RatingCount(r.rating, COUNT(r)) FROM Review r
    WHERE r.order.product.id = :productId
    GROUP BY r.rating
    """)
    List<RatingCount> findByProduct_IdRatingSummary(@Param("productId") Long productId);

    @Query("""
    SELECT r FROM Review r
    WHERE r.order.product.id = :productId ORDER BY r.createdAt DESC
    """)
    List<Review> findReviewByCreatedAt(@Param("productId") Long productId, Pageable pageable);
}
