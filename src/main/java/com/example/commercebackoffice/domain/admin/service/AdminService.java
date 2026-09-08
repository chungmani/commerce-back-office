package com.example.commercebackoffice.domain.admin.service;

import com.example.commercebackoffice.common.exception.*;
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
            throw new SuperAdminSignupNotAllowedException("슈퍼관리자로 가입할 수 없습니다.");
        }

        boolean existEmail = adminRepository.existsByEmail(request.email());
        if (existEmail) {
            throw new EmailAlreadyExistsException("이미 가입한 이메일입니다.");
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
                .orElseThrow(() -> new InvalidLoginException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new InvalidLoginException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        if (!admin.getState().canLogin()) {
            throw new AdminLoginNotAllowedException(admin.getState().getMessage());
        }

        return admin;
    }


    // TODO: 관리자 조회, 수정, 삭제 부분에서 슈퍼관리자 인증/인가 로직 구현 필요
    // 관리자 리스트 조회
    public Page<GetAdminsResponse> getAll(String keyword, AdminState state, AdminRole role, Pageable pageable) {
        if (pageable.getPageNumber() < 1) {
            throw new IllegalStateException("잘못된 페이지 요청입니다.");
        }
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());

        Page<Admin> admins = adminRepository.findAllByKeywordAndFilter(keyword, state, role, pageable);
        return admins.map(GetAdminsResponse::from);
    }

    // 관리자 상세 조회
    public GetAdminResponse getOne(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new NotFoundAdminException("관리자를 찾을 수 없습니다.")
        );

        return GetAdminResponse.from(admin);
    }


    // 관리자 정보 수정
    @Transactional
    public UpdateAdminResponse update(Long adminId, UpdateAdminRequest request) {

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new NotFoundAdminException("관리자를 찾을 수 없습니다.")
        );

        if (request.getEmail() != null) {
            boolean isPresent = adminRepository.existsByEmailAndIdNot(request.getEmail(), adminId);
            if (isPresent) {
                throw new EmailAlreadyExistsException("이미 가입한 이메일입니다.");
            }
        }

        admin.updateAdmin(request.getName(), request.getEmail(), request.getPhoneNumber());
        return UpdateAdminResponse.from(admin);
    }

    // 관리자 역할 변경
    @Transactional
    public ChangeAdminRoleResponse changeAdminRole(Long adminId, ChangeAdminRoleRequest request) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new NotFoundAdminException("관리자를 찾을 수 없습니다.")
        );

        admin.changeRole(request.role());
        return ChangeAdminRoleResponse.from(admin);
    }
}
