package com.example.commercebackoffice.domain.admin.controller;

import com.example.commercebackoffice.domain.admin.dto.CreateAdminRequest;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminResponse;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<CreateAdminResponse> create(@RequestBody CreateAdminRequest request) {
        if (request.equals(AdminRole.SUPER_ADMIN)) {
            throw new IllegalStateException("슈퍼관리자로 가입할 수 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.create(request));
    }


}
