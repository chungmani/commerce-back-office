package com.example.commercebackoffice.domain.admin.service;

import com.example.commercebackoffice.common.exception.AdminLoginNotAllowedException;
import com.example.commercebackoffice.common.exception.EmailAlreadyExistsException;
import com.example.commercebackoffice.common.exception.InvalidLoginException;
import com.example.commercebackoffice.common.exception.SuperAdminSignupNotAllowedException;
import com.example.commercebackoffice.common.security.PasswordEncoder;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminRequest;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminResponse;
import com.example.commercebackoffice.domain.admin.dto.LoginRequest;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.enums.AdminState;
import com.example.commercebackoffice.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
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
}
