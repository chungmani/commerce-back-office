package com.example.commercebackoffice.domain.customer.repository;

import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""

            SELECT c FROM Customer c
        WHERE (c.deletedAt IS NULL) AND (:keyword IS NULL OR (c.name LIKE CONCAT('%', :keyword, '%')) OR 
            (c.email LIKE CONCAT('%', :keyword, '%'))) AND (:state IS NULL OR c.state = :state)
    """)
    Page<Customer> findAllByKeywordAndFilter(@Param("keyword") String keyword, @Param("state") CustomerState state, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.id =:customerId AND c.deletedAt IS NULL")
    Optional<Customer> findByIdNotDeleted(@Param("customerId") Long customerId);

    boolean existsByEmail(String email);

    }
