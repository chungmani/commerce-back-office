package com.example.commercebackoffice.domain.review.repository;

import com.example.commercebackoffice.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
