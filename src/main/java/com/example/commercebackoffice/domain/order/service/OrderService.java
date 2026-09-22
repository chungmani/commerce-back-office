package com.example.commercebackoffice.domain.order.service;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.service.CustomerService;
import com.example.commercebackoffice.domain.order.dto.CreateOrderRequest;
import com.example.commercebackoffice.domain.order.dto.CreateOrderResponse;
import com.example.commercebackoffice.domain.order.dto.GetOrdersResponse;
import com.example.commercebackoffice.domain.order.entity.Order;
import com.example.commercebackoffice.domain.order.enums.OrderState;
import com.example.commercebackoffice.domain.order.repository.OrderRepository;
import com.example.commercebackoffice.domain.product.entity.Product;
import com.example.commercebackoffice.domain.product.enums.ProductState;
import com.example.commercebackoffice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final AdminService adminService;
    private final ProductService productService;
    private final CustomerService customerService;

    // 주문 생성
    @Transactional
    public CreateOrderResponse create(CreateOrderRequest request, Long adminId) {
        Admin admin = adminService.getAdmin(adminId);
        Product product = productService.getProductById(request.productId());
        Customer customer = customerService.getCustomerById(request.customerId());

        if (product.getState().equals(ProductState.DISCONTINUED) || product.getState().equals(ProductState.SOLD_OUT)) {
            throw new BusinessException(ResponseCode.INVALID_ORDER);
        }

        String orderNumber = createOrderNumber();
        Order order = new Order(customer, product, request.quantity(), admin, orderNumber);
        productService.changeStock(order.getProduct().getId(), order.getQuantity());

        Order savedOrder = orderRepository.save(order);
        return CreateOrderResponse.from(savedOrder);
    }

    // 주문 번호 생성
    private String createOrderNumber() {
        return "ORDER-" + UUID.randomUUID().toString().substring(0, 12);
    }

    // 주문 전체 조회
    public Page<GetOrdersResponse> findAll(String keyword, OrderState state, Pageable pageable) {
        if (pageable.getPageNumber() < 1) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());

        Page<Order> orders = orderRepository.findAllByKeywordAndState(keyword, state, pageable);
        return orders.map(GetOrdersResponse::from);
    }

    // 주문 가져오는 공통 메서드
    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(ResponseCode.ORDER_NOT_FOUND)
        );
    }

}
