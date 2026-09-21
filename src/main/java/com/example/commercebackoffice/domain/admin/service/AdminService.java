package com.example.commercebackoffice.domain.admin.service;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.common.security.PasswordEncoder;
import com.example.commercebackoffice.domain.admin.dto.*;
import com.example.commercebackoffice.domain.auth.dto.LoginRequest;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.enums.AdminState;
import com.example.commercebackoffice.domain.admin.repository.AdminRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public CreateAdminResponse create(CreateAdminRequest request) {

        if (request.role() == AdminRole.SUPER_ADMIN) {
            throw new BusinessException(ResponseCode.SUPER_ADMIN_SIGNUP_NOT_ALLOWED);
        }

        boolean existEmail = adminRepository.existsByEmail(request.email());
        if (existEmail) {
            throw new BusinessException(ResponseCode.EMAIL_ALREADY_EXISTS);
        }

        // 비밀번호 암호화
        String passwordHashed = passwordEncoder.encode(request.password());

        Admin admin = new Admin(
                request.name(), request.email(), passwordHashed,
                request.phoneNumber(), request.role()
        );

        Admin savedAdmin = adminRepository.save(admin);
        return CreateAdminResponse.from(savedAdmin);
    }

    // 로그인
    public Admin login(LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(ResponseCode.INVALID_LOGIN));

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new BusinessException(ResponseCode.INVALID_LOGIN);
        }

        if (admin.getDeletedAt() != null) {
            throw new BusinessException(ResponseCode.INVALID_LOGIN);
        }

        if (!admin.getState().canLogin()) {
            throw new BusinessException(ResponseCode.ADMIN_LOGIN_NOT_ALLOWED);
        }

        return admin;
    }

    // 관리자 리스트 조회
    public Page<GetAdminsResponse> getAll(String keyword, AdminState state, AdminRole role, Pageable pageable) {
        if (pageable.getPageNumber() < 1) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());

        Page<Admin> admins = adminRepository.findAllByKeywordAndFilter(keyword, state, role, pageable);
        return admins.map(GetAdminsResponse::from);
    }

    // 관리자 상세 조회
    public GetAdminResponse getOne(Long adminId) {
        Admin admin = getAdminById(adminId);

        return GetAdminResponse.from(admin);
    }

    // 관리자 정보 수정
    @Transactional
    public UpdateAdminResponse update(Long adminId, UpdateAdminRequest request) {

        Admin admin = getAdminById(adminId);

        if (request.getEmail() != null) {
            boolean isPresent = adminRepository.existsByEmailAndIdNot(request.getEmail(), adminId);
            if (isPresent) {
                throw new BusinessException(ResponseCode.EMAIL_ALREADY_EXISTS);
            }
        }

        admin.updateAdmin(request.getName(), request.getEmail(), request.getPhoneNumber());
        return UpdateAdminResponse.from(admin);
    }

    // 관리자 역할 변경
    @Transactional
    public ChangeAdminRoleResponse changeAdminRole(Long adminId, ChangeAdminRoleRequest request) {
        Admin admin = getAdminById(adminId);

        admin.changeRole(request.role());
        return ChangeAdminRoleResponse.from(admin);
    }

    // 관리자 상태 변경
    @Transactional
    public ChangeAdminStateResponse changeAdminState(Long adminId, ChangeAdminStateRequest request) {
        Admin admin = getAdminById(adminId);
        admin.changeState(request.state());
        return ChangeAdminStateResponse.from(admin);
    }

    // 관리자 삭제
    @Transactional
    public void deleteAdmin(Long adminId) {
        Admin admin = getAdminById(adminId);
        admin.delete();
    }

    // 관리자 승인
    @Transactional
    public ApproveAdminResponse approve(Long adminId) {
        Admin admin = getAdminById(adminId);
        admin.approve();
        return ApproveAdminResponse.from(admin);
    }

    // 관리자 거부
    @Transactional
    public RejectAdminResponse reject(Long adminId, RejectAdminRequest request) {
        Admin admin = getAdminById(adminId);
        String reason = request.reason();
        admin.reject(reason);
        return RejectAdminResponse.from(admin);
    }

    // 프로필 조회
    public GetProfileResponse getProfile(Long adminId) {
        Admin admin = getAdminById(adminId);
        if (admin.getState() != AdminState.ACTIVE) {
            throw new BusinessException(ResponseCode.FORBIDDEN_ADMIN);
        }
        return GetProfileResponse.from(admin);
    }

    // 프로필 수정
    @Transactional
    public UpdateProfileResponse updateProfile(Long adminId, UpdateProfileRequest request) {
        Admin admin = getAdminById(adminId);

        boolean existEmail = adminRepository.existsByEmail(request.email());
        if (existEmail && !request.email().equals(admin.getEmail())) {
            throw new BusinessException(ResponseCode.EMAIL_ALREADY_EXISTS);
        }

        admin.updateAdmin(request.name(), request.email(), request.phoneNumber());
        return UpdateProfileResponse.from(admin);
    }

    // 비밀번호 변경
    @Transactional
    public void updatePw(Long adminId, UpdatePwRequest request) {
        Admin admin = getAdminById(adminId);
        if (!passwordEncoder.matches(request.currentPassword(), admin.getPassword())) {
            throw new BusinessException(ResponseCode.INVALID_PASSWORD);
        }
        if (request.currentPassword().equals(request.newPassword())) {
            throw new BusinessException(ResponseCode.DUPLICATED_PASSWORD);
        }
        String passwordHashed = passwordEncoder.encode(request.newPassword());
        admin.updatePw(passwordHashed);
    }

    // product에서 관리자 가져오기
    public Admin getOperationAdmin(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(
                () -> new BusinessException(ResponseCode.ADMIN_NOT_FOUND)
        );
    }

    // 공통메서드
    private Admin getAdminById(Long adminId) {
        return adminRepository.findByIdNotDeleted(adminId).orElseThrow(
                () -> new BusinessException(ResponseCode.ADMIN_NOT_FOUND)
        );
    }


}
