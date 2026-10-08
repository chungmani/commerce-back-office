package com.example.commercebackoffice.domain.dashboard.service;

import com.example.commercebackoffice.domain.admin.service.AdminService;
import com.example.commercebackoffice.domain.customer.dto.CustomerStateChart;
import com.example.commercebackoffice.domain.customer.service.CustomerService;
import com.example.commercebackoffice.domain.dashboard.dto.ChartsData;
import com.example.commercebackoffice.domain.dashboard.dto.LatestOrderList;
import com.example.commercebackoffice.domain.dashboard.dto.SummaryStatistics;
import com.example.commercebackoffice.domain.dashboard.dto.WidgetsData;
import com.example.commercebackoffice.domain.order.service.OrderService;
import com.example.commercebackoffice.domain.product.dto.CategoryProductCount;
import com.example.commercebackoffice.domain.product.service.ProductService;
import com.example.commercebackoffice.domain.review.dto.RatingSummary;
import com.example.commercebackoffice.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final AdminService adminService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ReviewService reviewService;


    // Summary 통계
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

    // 위젯 데이터
    public WidgetsData widgetsData() {
        // 총 매출
        long totalOrderPrice = orderService.totalOrderPrice();

        // 오늘 매출
        long todayOrderPrice = orderService.todayOrderPrice();

        // 준비중 주문수
        long preparingOrder = orderService.preparingOrder();

        // 배송중 주문수
        long shippingOrder = orderService.shippingOrder();

        // 배송완료 주문수
        long deliveredOrder = orderService.deliveredOrder();

        // 재고부족 상품수
        long lowStock = productService.lowStockCount();

        // 재고 없음(품절) 상품수
        long soldOutProduct = productService.soldOutProduct();

        return new WidgetsData(totalOrderPrice, todayOrderPrice, preparingOrder,
                shippingOrder, deliveredOrder, lowStock, soldOutProduct
        );
    }

    // 차트데이터
    public ChartsData chartsData() {
        // 리뷰 평점 분포
        RatingSummary ratingSummary = reviewService.ratingSummary();

        // 고객 상태 분포
        CustomerStateChart customerStateChart = customerService.customerStateChart();

        // 상품 카테고리 분포
        List<CategoryProductCount> categoryProductCounts = productService.categoryChart();

        return new ChartsData(ratingSummary, customerStateChart, categoryProductCounts);
    }

    // 최근 주문 목록
    public List<LatestOrderList> getLatestOrderList() {
        return orderService.getLatestOrderList();
    }
}
