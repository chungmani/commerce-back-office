package com.example.commercebackoffice.domain.customer.repository;

import com.example.commercebackoffice.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
