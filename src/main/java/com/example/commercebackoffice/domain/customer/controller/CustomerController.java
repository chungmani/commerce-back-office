package com.example.commercebackoffice.domain.customer.controller;

import com.example.commercebackoffice.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
}
