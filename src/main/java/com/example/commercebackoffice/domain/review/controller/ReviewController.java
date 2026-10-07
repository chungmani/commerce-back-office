package com.example.commercebackoffice.domain.review.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.review.dto.GetReviewResponse;
import com.example.commercebackoffice.domain.review.dto.GetReviewsResponse;
import com.example.commercebackoffice.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<GetReviewsResponse>>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer rating,
            @PageableDefault(page = 1, size = 10, sort = "rating", direction = Sort.Direction.ASC)Pageable pageable
            ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, reviewService.findAll(keyword, rating, pageable)));
    }

    // 리뷰 상세 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<GetReviewResponse>> getOne(@PathVariable Long reviewId) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, reviewService.findOne(reviewId)));
    }
}
