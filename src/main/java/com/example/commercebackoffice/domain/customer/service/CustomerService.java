package com.example.commercebackoffice.domain.customer.service;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.customer.dto.GetCustomerResponse;
import com.example.commercebackoffice.domain.customer.dto.GetCustomersResponse;
import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import com.example.commercebackoffice.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    // 고객 전체 조회
    public Page<GetCustomersResponse> findAll(String keyword, CustomerState state, Pageable pageable) {
        if (pageable.getPageNumber() < 1) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());

        Page<Customer> customers = customerRepository.findAllByKeywordAndFilter(keyword, state, pageable);
        return customers.map(GetCustomersResponse::from);
    }

    // 고객 상세 조회
    public GetCustomerResponse findOne(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new BusinessException(ResponseCode.CUSTOMER_NOT_FOUND)
        );
        return GetCustomerResponse.from(customer);
    }
}
