package com.example.commercebackoffice.domain.review.service;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.review.dto.GetReviewsResponse;
import com.example.commercebackoffice.domain.review.entity.Review;
import com.example.commercebackoffice.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 전체 조회
    public Page<GetReviewsResponse> findAll(String keyword, Integer rating, Pageable pageable) {
        if (pageable.getPageNumber() < 1) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());

        Page<Review> reviews = reviewRepository.findAllByKeywordAndRating(keyword, rating, pageable);

        return reviews.map(GetReviewsResponse::from);
    }
}
