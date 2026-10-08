package com.example.commercebackoffice.domain.customer.service;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.customer.dto.*;
import com.example.commercebackoffice.domain.customer.entity.Customer;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import com.example.commercebackoffice.domain.customer.repository.CustomerRepository;
import com.example.commercebackoffice.domain.order.dto.CustomerOrderSummary;
import com.example.commercebackoffice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;

    // 고객 전체 조회
    public Page<GetCustomersResponse> findAll(String keyword, CustomerState state, Pageable pageable) {
        if (pageable.getPageNumber() < 1) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());

        Page<Customer> customers = customerRepository.findAllByKeywordAndFilter(keyword, state, pageable);

        List<CustomerOrderSummary> summaries  = orderService.getSummary();
        Map<Long, CustomerOrderSummary> summaryMap = summaries.stream()
                .collect(Collectors.toMap(
                        customerOrderSummary -> customerOrderSummary.customerId(),
                        Function.identity()
                ));

        return customers.map(customer -> {
            CustomerOrderSummary summary = summaryMap.get(customer.getId());
            if (summary == null) {
                return GetCustomersResponse.from(customer, 0, 0);
            }
            return GetCustomersResponse.from(customer, summary.totalOrderCount(), summary.totalOrderPrice());
        });
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
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findByIdNotDeleted(customerId).orElseThrow(
                () -> new BusinessException(ResponseCode.CUSTOMER_NOT_FOUND)
        );
    }

    // 전체 고객 수 조회
    public long countAll() {
        return customerRepository.countAll();
    }

    // 활성화 고객 수 조회
    public long countActive() {
        return customerRepository.countActiveCustomer();
    }

    // 상태별 고객 수 조회
    public CustomerStateChart customerStateChart() {
        List<CustomerStateCount> stateCounts = customerRepository.countCustomerState();

        Map<CustomerState, Long> customerStateLongMap = stateCounts.stream()
                .collect(Collectors.toMap(
                        customerStateCount -> customerStateCount.getState(),
                        customerStateCount -> customerStateCount.getCount()
                ));

        return new CustomerStateChart(
                customerStateLongMap.getOrDefault(CustomerState.ACTIVE, 0L),
                customerStateLongMap.getOrDefault(CustomerState.INACTIVE, 0L),
                customerStateLongMap.getOrDefault(CustomerState.SUSPENDED, 0L)
        );
    }
}
