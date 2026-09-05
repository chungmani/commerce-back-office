package com.example.commercebackoffice.domain.admin.controller;

import com.example.commercebackoffice.domain.admin.dto.*;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.enums.AdminState;
import com.example.commercebackoffice.domain.admin.service.AdminService;
import com.example.commercebackoffice.domain.admin.dto.GetAdminsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 관리자 리스트 조회
    @GetMapping
    public ResponseEntity<Page<GetAdminsResponse>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) AdminState state,
            @RequestParam(required = false) AdminRole role,
            @PageableDefault(page = 1, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(adminService.getAll(keyword, state, role, pageable));
    }

    // 관리자 상세 조회
    @GetMapping("/{adminId}")
    public ResponseEntity<GetAdminResponse> getOne(@PathVariable Long adminId) {
        return ResponseEntity.ok(adminService.getOne(adminId));
    }

}
