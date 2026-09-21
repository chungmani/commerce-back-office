package com.example.commercebackoffice.domain.customer.service;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.customer.dto.*;
import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import com.example.commercebackoffice.domain.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
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
        Customer customer = getCustomerById(customerId);
        return GetCustomerResponse.from(customer);
    }

    // 고객 정보 수정
    @Transactional
    public UpdateCustomerResponse update(Long customerId, UpdateCustomerRequest request) {
        Customer customer = getCustomerById(customerId);
        boolean isExist = customerRepository.existsByEmail(request.email());
        if (isExist && !customer.getEmail().equals(request.email())) {
            throw new BusinessException(ResponseCode.EMAIL_ALREADY_EXISTS);
        }
        customer.updateCustomer(request.name(), request.email(), request.phoneNumber());
        return UpdateCustomerResponse.from(customer);
    }

    // 고객 상태 변경
    @Transactional
    public ChangeCustomerStateResponse changeState(Long customerId, ChangeCustomerStateRequest request) {
        Customer customer = getCustomerById(customerId);
        customer.changeState(request.state());
        return ChangeCustomerStateResponse.from(customer);
    }

    // 고객 삭제(탈퇴)
    @Transactional
    public void delete(Long customerId) {
        Customer customer = getCustomerById(customerId);
        customer.delete();
    }

    // 공통 메서드
    private Customer getCustomerById(Long customerId) {
        return customerRepository.findByIdNotDeleted(customerId).orElseThrow(
                () -> new BusinessException(ResponseCode.CUSTOMER_NOT_FOUND)
        );
    }


}
