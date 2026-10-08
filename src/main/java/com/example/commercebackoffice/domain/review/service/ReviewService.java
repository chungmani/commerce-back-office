package com.example.commercebackoffice.domain.review.service;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.review.dto.*;
import com.example.commercebackoffice.domain.review.entity.Review;
import com.example.commercebackoffice.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // 리뷰 상세 조회
    public GetReviewResponse findOne(Long reviewId) {
        Review review = getReview(reviewId);
        return GetReviewResponse.from(review);
    }

    // 리뷰 삭제
    @Transactional
    public void delete(Long reviewId) {
        Review review = getReview(reviewId);
        reviewRepository.delete(review);
    }

    // 리뷰 통계
    public ReviewSummaryResponse reviewSummaryResponse(Long productId) {
        // productId가 같은 리뷰의 평균 평점
        double reviewAverage = reviewRepository.findByProduct_IdReviewAverage(productId);
        reviewAverage = Math.round(reviewAverage * 10) / 10.0;

        // productId의 전체 리뷰 개수
        long reviewCount = reviewRepository.findByProduct_IdReviewCount(productId);

        // productId의 평점별 개수
        List<RatingCount> ratingCountList = reviewRepository.findByProduct_IdRatingSummary(productId);
        Map<Integer, Long> ratingCountMap = ratingCountList.stream()
                .collect(Collectors.toMap(
                        ratingCount -> ratingCount.getRating(),
                        ratingCount -> ratingCount.getCount()
                ));

        RatingSummary ratingSummary = new RatingSummary(
                ratingCountMap.getOrDefault(5, 0L),
                ratingCountMap.getOrDefault(4, 0L),
                ratingCountMap.getOrDefault(3, 0L),
                ratingCountMap.getOrDefault(2, 0L),
                ratingCountMap.getOrDefault(1, 0L)
        );

        return ReviewSummaryResponse.from(reviewAverage, reviewCount, ratingSummary);
    }

    // 최근 리뷰 목록
    public List<LatestReviewResponse> latestReviewResponses(Long productId) {
        Pageable pageable = PageRequest.ofSize(3);
        List<Review> reviews = reviewRepository.findReviewByCreatedAt(productId, pageable);

        return reviews.stream()
                .map(LatestReviewResponse::from)
                .toList();
    }

    // 공통 메서드
    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new BusinessException(ResponseCode.REVIEW_NOT_FOUND)
        );
    }

    // 전체 리뷰 수 조회
    public long countAll() {
        return reviewRepository.countAll();
    }

    // 평균 평점
    public double averageRating() {
        return reviewRepository.averageRating();
    }

    // 별점별 개수
    public RatingSummary ratingSummary() {
        List<RatingCount> ratingCountList = reviewRepository.countALlByRating();
        Map<Integer, Long> ratingCountMap = ratingCountList.stream()
                .collect(Collectors.toMap(
                        ratingCount -> ratingCount.getRating(),
                        ratingCount -> ratingCount.getCount()
                ));

        return  new RatingSummary(
                ratingCountMap.getOrDefault(5, 0L),
                ratingCountMap.getOrDefault(4, 0L),
                ratingCountMap.getOrDefault(3, 0L),
                ratingCountMap.getOrDefault(2, 0L),
                ratingCountMap.getOrDefault(1, 0L)
        );
    }
}
