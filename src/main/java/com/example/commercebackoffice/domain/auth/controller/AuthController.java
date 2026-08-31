package com.example.commercebackoffice.domain.auth.controller;

import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.enums.AdminState;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import com.example.commercebackoffice.domain.auth.dto.GetAdminsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/admins")
public class AuthController {

    private final AdminService adminService;

    // 관리자 리스트 조회
    @GetMapping
    public ResponseEntity<Page<GetAdminsResponse>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) AdminState state,
            @RequestParam(required = false) AdminRole role,
            @PageableDefault(page = 1, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(adminService.getAll(keyword, state, role, pageable));
    }

}
