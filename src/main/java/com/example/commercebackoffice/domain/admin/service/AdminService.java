package com.example.commercebackoffice.domain.admin.service;

import com.example.commercebackoffice.common.config.PasswordEncoder;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminRequest;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminResponse;
import com.example.commercebackoffice.domain.admin.entity.Admin;
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

        boolean existEmail = adminRepository.existsByEmail(request.email());
        if (!existEmail) {
            throw new IllegalStateException("이미 가입한 이메일입니다.");
        }

        // 비밀번호 암호화
        String passwordHashed = passwordEncoder.encode(request.password());

        Admin admin = new Admin(
                request.name(), request.email(), request.password(),
                request.phoneNumber(), request.role()
        );

        Admin savedAdmin = adminRepository.save(admin);
        return CreateAdminResponse.from(savedAdmin);
    }
}
