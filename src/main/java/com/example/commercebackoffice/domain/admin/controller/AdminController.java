package com.example.commercebackoffice.domain.admin.controller;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<CreateAdminResponse>> create(@Valid @RequestBody CreateAdminRequest request) {
        return ResponseEntity.status(ResponseCode.CREATED.getStatus()).body(ApiResponse.success(ResponseCode.CREATED, adminService.create(request)));
    }

    // 관리자 리스트 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<GetAdminsResponse>>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) AdminState state,
            @RequestParam(required = false) AdminRole role,
            @PageableDefault(page = 1, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, adminService.getAll(keyword, state, role, pageable)));
    }

    // 관리자 상세 조회
    @GetMapping("/{adminId}")
    public ResponseEntity<ApiResponse<GetAdminResponse>> getOne(@PathVariable Long adminId) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, adminService.getOne(adminId)));
    }


    // 관리자 정보 수정
    @PatchMapping("/{adminId}")
    public ResponseEntity<ApiResponse<UpdateAdminResponse>> update(
            @PathVariable Long adminId,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, adminService.update(adminId, request)));
    }

    // 관리자 역할 변경
    @PatchMapping("/{adminId}/role")
    public ResponseEntity<ApiResponse<ChangeAdminRoleResponse>> changeAdminRole(
            @PathVariable Long adminId, @Valid @RequestBody ChangeAdminRoleRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, adminService.changeAdminRole(adminId, request)));
    }

    // 관리자 상태 변경
    @PatchMapping("/{adminId}/state")
    public ResponseEntity<ApiResponse<ChangeAdminStateResponse>> changeAdminState(
            @PathVariable Long adminId, @Valid @RequestBody ChangeAdminStateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.OK, adminService.changeAdminState(adminId, request)));
    }
}
