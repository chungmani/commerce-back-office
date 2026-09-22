package com.example.commercebackoffice.domain.order.repository;

import com.example.commercebackoffice.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
