package com.example.commercebackoffice.domain.customer.service;

import com.example.commercebackoffice.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
}
