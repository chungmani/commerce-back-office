package com.example.commercebackoffice.domain.admin.service;

import com.example.commercebackoffice.common.security.PasswordEncoder;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminRequest;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminResponse;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("정상 회원가입")
    void signup() {
        // given
        CreateAdminRequest request = new CreateAdminRequest(
                "채원", "test@test.com", "Dlcodnjs12#",
                "010-0000-0000", AdminRole.CS_ADMIN
        );

        when(adminRepository.existsByEmail(request.email()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.password()))
                .thenReturn("passwordHashed");

        Admin admin = new Admin(request.name(), request.email(), "passwordHashed",
                request.phoneNumber(), request.role());

        ReflectionTestUtils.setField(admin, "id", 1L);
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);

        // when
        CreateAdminResponse response = adminService.create(request);

        // then
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals(request.name(), response.name());
        assertEquals(request.email(), response.email());
        assertEquals(request.phoneNumber(), response.phoneNumber());
        assertEquals(request.role(), response.role());
        verify(adminRepository).save(adminCaptor.capture());
        assertEquals("passwordHashed", adminCaptor.getValue().getPassword());
    }

    @Test
    @DisplayName("이미 회원가입한 이메일이면 회원가입 실패")
    void duplicateEmailCheck() {
        // given
        CreateAdminRequest request = new CreateAdminRequest(
                "채원", "test@test.com", "Dlcodnjs12#",
                "010-0000-0000", AdminRole.CS_ADMIN
        );

        when(adminRepository.existsByEmail(request.email()))
                .thenReturn(true);

        // when & then
       IllegalStateException exception = assertThrows(IllegalStateException.class, () -> adminService.create(request));
       assertEquals("이미 가입한 이메일입니다.", exception.getMessage() );

    }

    @Test
    @DisplayName("슈퍼관리자로 회원가입시 회원가입 실패")
    void signupWithSuperAdmin() {
        // given
        CreateAdminRequest request = new CreateAdminRequest(
                "채원", "test@test.com", "Dlcodnjs12#",
                "010-0000-0000", AdminRole.SUPER_ADMIN
        );

        // when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.create(request));
        assertEquals("슈퍼관리자로 가입할 수 없습니다.", e.getMessage());
    }
}