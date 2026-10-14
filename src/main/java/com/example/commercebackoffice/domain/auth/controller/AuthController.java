package com.example.commercebackoffice.domain.auth.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.common.utils.JwtUtil;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import com.example.commercebackoffice.domain.auth.dto.LoginRequest;
import com.example.commercebackoffice.domain.auth.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        Admin admin = adminService.login(request);
        String token = jwtUtil.generateToken(admin.getEmail(), admin.getId(), admin.getRole());
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, LoginResponse.from(admin, token)));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(ResponseCode.NO_CONTENT.getStatus()).build();
    }

}
