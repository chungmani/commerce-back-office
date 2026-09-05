package com.example.commercebackoffice.domain.auth.controller;

import com.example.commercebackoffice.domain.auth.dto.LoginRequest;
import com.example.commercebackoffice.domain.auth.dto.LoginResponse;
import com.example.commercebackoffice.domain.auth.dto.SessionAdmin;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AdminService adminService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request, HttpSession session
    ) {
        Admin admin = adminService.login(request);
        session.setAttribute("loginAdmin", SessionAdmin.from(admin));
        return ResponseEntity.ok(LoginResponse.from(admin));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginAdmin", required = false)
            SessionAdmin sessionAdmin, HttpSession session
    ) {
        if (sessionAdmin == null) {
            return ResponseEntity.badRequest().build();
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
