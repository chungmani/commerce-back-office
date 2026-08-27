package com.example.commercebackoffice.domain.admin.service;

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

        if (request.role().equals(AdminRole.SUPER_ADMIN)) {
            throw new IllegalStateException("슈퍼관리자로 가입할 수 없습니다.");
        }

        boolean existEmail = adminRepository.existsByEmail(request.email());
        if (existEmail) {
            throw new IllegalStateException("이미 가입한 이메일입니다.");
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
                .orElseThrow(() -> new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        if (admin.getState().equals(AdminState.PENDING)) {
            throw new IllegalStateException("승인대기 중인 계정입니다.");
        }
        if (admin.getState().equals(AdminState.INACTIVE)) {
            throw new IllegalStateException("비활성화된 계정입니다.");
        }
        if (admin.getState().equals(AdminState.SUSPENDED)) {
            throw new IllegalStateException("정지된 계정입니다.");
        }
        if (admin.getState().equals(AdminState.DENIED)) {
            throw new IllegalStateException("거부된 계정입니다.");
        }

        return admin;
    }
}
