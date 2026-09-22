package com.example.commercebackoffice.domain.order.repository;

import com.example.commercebackoffice.domain.order.entity.Order;
import com.example.commercebackoffice.domain.order.enums.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    SELECT o FROM Order o
        WHERE (:keyword IS NULL OR (o.orderNumber LIKE CONCAT('%', :keyword, '%')) OR (o.customer.name LIKE CONCAT('%', :keyword, '%')))
            AND (:state IS NULL OR o.state = :state) 
    """)
    Page<Order> findAllByKeywordAndState(@Param("keyword") String keyword, @Param("state") OrderState state, Pageable pageable);
}
