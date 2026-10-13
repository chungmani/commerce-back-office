package com.example.commercebackoffice.domain.customer.service;

import com.example.commercebackoffice.domain.order.dto.CustomerOrderSummary;
import com.example.commercebackoffice.domain.order.repository.OrderRepository;
import com.example.commercebackoffice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerOrderSummaryService {

    private final OrderRepository orderRepository;

    public List<CustomerOrderSummary> getCustomerOrderSummary() {
        return orderRepository.getCustomerOrderSummary();
    }
}
