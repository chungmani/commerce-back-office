package com.example.commercebackoffice.domain.customer.entity;

import com.example.commercebackoffice.common.entity.BaseEntity;
import com.example.commercebackoffice.domain.customer.enums.CustomerState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private CustomerState state;

    public Customer(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.state = CustomerState.ACTIVE;
    }

    public void updateCustomer(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
