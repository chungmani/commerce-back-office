package com.example.commercebackoffice.domain.product.service;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.product.dto.GetProductResponse;
import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;
import com.example.commercebackoffice.domain.product.repository.ProductRepository;
import com.example.commercebackoffice.domain.review.dto.LatestReviewResponse;
import com.example.commercebackoffice.domain.review.dto.RatingSummary;
import com.example.commercebackoffice.domain.review.dto.ReviewSummaryResponse;
import com.example.commercebackoffice.domain.review.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ProductService productService;

    @Test
    void 상품_상세_조회시_상품정보와_리뷰정보를_함께_반환한다() {
        // given
        Long productId = 1L;
        Product product = mock(Product.class);

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(product.getName()).willReturn("테스트 상품");
        given(product.getCategory()).willReturn("식품");
        given(product.getPrice()).willReturn(10000);
        given(product.getStock()).willReturn(5);
        given(product.getState()).willReturn(ProductState.ON_SALE);

        Admin admin = mock(Admin.class);

        given(admin.getName()).willReturn("관리자");
        given(admin.getEmail()).willReturn("admin@test.com");

        given(product.getAdmin()).willReturn(admin);

        ReviewSummaryResponse summary = new ReviewSummaryResponse(
                4.5, 2L,
                new RatingSummary(1L, 0L, 0L, 0L, 1L)
        );

        List<LatestReviewResponse> latestReviews = List.of(
                new LatestReviewResponse("테스트 고객", 5, "좋아요!", null),
                new LatestReviewResponse("다른 고객", 4, "괜찮아요!", null) );

        given(reviewService.reviewSummaryResponse(productId)).willReturn(summary);
        given(reviewService.latestReviewResponses(productId)).willReturn(latestReviews);

        // when
        GetProductResponse response = productService.findOne(productId);

        // then
        assertThat(response.name()).isEqualTo("테스트 상품");
        assertThat(response.price()).isEqualTo(10000);
        assertThat(response.summaryResponse()).isEqualTo(summary);
        assertThat(response.latestReviewResponses()).hasSize(2).containsExactlyElementsOf(latestReviews);
    }
}
