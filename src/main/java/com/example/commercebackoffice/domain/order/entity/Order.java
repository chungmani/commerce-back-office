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
}
