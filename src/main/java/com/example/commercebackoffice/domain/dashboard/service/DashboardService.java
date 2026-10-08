package com.example.commercebackoffice.domain.dashboard.service;

import com.example.commercebackoffice.domain.admin.service.AdminService;
import com.example.commercebackoffice.domain.customer.service.CustomerService;
import com.example.commercebackoffice.domain.dashboard.dto.SummaryStatistics;
import com.example.commercebackoffice.domain.order.service.OrderService;
import com.example.commercebackoffice.domain.product.service.ProductService;
import com.example.commercebackoffice.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AdminService adminService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    // Summary 통계
    @Transactional(readOnly = true)
    public SummaryStatistics summaryStatistics() {
        // 전체 관리자 수
        long totalAdmin = adminService.countAll();

        // 활성 관리자 수
        long activeAdmin = adminService.countActive();

        // 전체 고객 수
        long totalCustomer = customerService.countAll();

        // 활성 고객 수
        long activeCustomer = customerService.countActive();

        // 전체 상품 수
        long totalProduct = productService.countAll();

        // 재고 부족 상품 수 (5개 이하)
        long lowStock = productService.lowStockCount();

        // 전체 주문 수
        long totalOrder = orderService.countAll();

        // 오늘 주문 수
        long todayOrder = orderService.todayOrder();

        // 전체 리뷰 수
        long totalReview = reviewService.countAll();

        // 평균 평점
        double averageRating = reviewService.averageRating();
        averageRating = Math.round(averageRating * 10) / 10.0;

        return new SummaryStatistics(totalAdmin, activeAdmin, totalCustomer, activeCustomer,
                totalProduct, lowStock, totalOrder, todayOrder, totalReview, averageRating
        );
    }
}
