package com.example.commercebackoffice.domain.admin.controller;

import com.example.commercebackoffice.domain.admin.dto.CreateAdminRequest;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminResponse;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CreateAdminResponse> create(@Valid @RequestBody CreateAdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.create(request));
    }


}
