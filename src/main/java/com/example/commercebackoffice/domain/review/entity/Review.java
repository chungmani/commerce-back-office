package com.example.commercebackoffice.domain.review.entity;

import com.example.commercebackoffice.common.entity.BaseEntity;
import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true, nullable = false)
    private Order order;

    public Review(String content, int rating, Order order) {
        if (rating < 1 || rating > 5) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }
        this.content = content;
        this.rating = rating;
        this.order = order;
    }
}
