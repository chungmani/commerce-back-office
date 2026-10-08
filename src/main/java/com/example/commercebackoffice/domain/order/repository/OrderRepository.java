package com.example.commercebackoffice.domain.order.repository;

import com.example.commercebackoffice.domain.order.dto.CustomerOrderSummary;
import com.example.commercebackoffice.domain.order.entity.Order;
import com.example.commercebackoffice.domain.order.enums.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    SELECT o FROM Order o
        WHERE (:keyword IS NULL OR (o.orderNumber LIKE CONCAT('%', :keyword, '%')) OR (o.customer.name LIKE CONCAT('%', :keyword, '%')))
            AND (:state IS NULL OR o.state = :state) 
    """)
    Page<Order> findAllByKeywordAndState(@Param("keyword") String keyword, @Param("state") OrderState state, Pageable pageable);

    long countByCustomer_id(Long customerId);

    @Query("SELECT SUM(o.productPrice * o.quantity) FROM Order o WHERE o.customer.id =:customerId")
    long totalPriceByCustomer_Id(@Param("customerId") Long customerId);

    @Query("""
    SELECT o.customer.id, COUNT(o), SUM(o.productPrice * o.quantity) FROM Order o
    GROUP BY o.customer.id
    """)
    List<CustomerOrderSummary> getCustomerOrderSummary();

    @Query("SELECT COUNT(o) FROM Order o")
    long countAll();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt >= :start AND o.createdAt < :end")
    long todayOrderCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COALESCE(SUM(o.productPrice * o.quantity), 0) FROM Order o WHERE o.state <> OrderState.CANCELED")
    long totalOrderPrice();

    @Query("""
    SELECT COALESCE(SUM(o.productPrice * o.quantity), 0) FROM Order o 
    WHERE o.state <> OrderState.CANCELED AND o.createdAt >= :start AND o.createdAt < :end
    """)
    long todayOrderPrice(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.state = OrderState.PREPARING")
    long preparingOrder();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.state = OrderState.SHIPPING")
    long shippingOrder();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.state = OrderState.DELIVERED")
    long deliveredOrder();
}
