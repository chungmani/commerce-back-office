package com.example.commercebackoffice.domain.order.entity;

import com.example.commercebackoffice.common.entity.BaseEntity;
import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.order.enums.OrderState;
import com.example.commercebackoffice.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int productPrice;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private String cancelReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Order(Customer customer, Product product, int quantity, Admin admin, String orderNumber) {
        if (quantity < 1) {
            throw new BusinessException(ResponseCode.ORDER_INVALID_QUANTITY);
        }
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.quantity = quantity;
        this.customer = customer;
        this.product = product;
        this.admin = admin;
        this.state = OrderState.PREPARING;
        this.orderNumber = orderNumber;
    }

    // 상태 변경 순서: 준비중 → 배송중 → 배송완료
    public void changeOrderState(OrderState state) {
        if (state == OrderState.SHIPPING && this.state == OrderState.PREPARING) {
            this.state = OrderState.SHIPPING;
            return;
        }
        if (state == OrderState.DELIVERED && this.state == OrderState.SHIPPING) {
            this.state = OrderState.DELIVERED;
        }

        throw new BusinessException(ResponseCode.NOT_ALLOWED_CHANGE_ORDER_STATE);
    }

    // 주문 취소는 준비중 상태에서만 허용합니다. (배송중/배송완료는 취소 불가)
    public void cancelOrder(String cancelReason) {
        if (this.state != OrderState.PREPARING) {
            throw new BusinessException(ResponseCode.NOT_ALLOWED_CHANGE_ORDER_STATE);
        }
        this.state = OrderState.CANCELED;
        this.cancelReason = cancelReason;
    }
}
