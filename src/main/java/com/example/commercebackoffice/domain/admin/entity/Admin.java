package com.example.commercebackoffice.domain.admin.entity;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.admin.dto.RejectAdminRequest;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.enums.AdminState;
import com.example.commercebackoffice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private AdminRole role;

    @Enumerated(EnumType.STRING)
    private AdminState state = AdminState.PENDING;

    @Column(name = "denied_reason")
    private String deniedReason;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "denied_at")
    private LocalDateTime deniedAt;

    public Admin(String name, String email, String password, String phoneNumber, AdminRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void updateAdmin(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void changeState(AdminState state) {
        this.state = state;
    }

    public void changeRole(AdminRole role) {
        this.role = role;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void approve() {
        if (this.getState() != AdminState.PENDING) {
            throw new BusinessException(ResponseCode.NOT_PENDING_ADMIN);
        }
        this.state = AdminState.ACTIVE;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(String reason) {
        if (this.getState() != AdminState.PENDING) {
            throw new BusinessException(ResponseCode.NOT_PENDING_ADMIN);
        }
        this.state = AdminState.DENIED;
        this.deniedReason = reason;
        this.deniedAt = LocalDateTime.now();
    }

}
