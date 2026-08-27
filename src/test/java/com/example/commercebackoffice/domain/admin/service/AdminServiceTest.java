package com.example.commercebackoffice.domain.admin.service;

import com.example.commercebackoffice.common.security.PasswordEncoder;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminRequest;
import com.example.commercebackoffice.domain.admin.dto.CreateAdminResponse;
import com.example.commercebackoffice.domain.admin.dto.LoginRequest;
import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.enums.AdminState;
import com.example.commercebackoffice.domain.admin.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

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

    private Admin admin;
    private CreateAdminRequest request;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        request = new CreateAdminRequest(
                "채원", "test@test.com", "Rlacodnjs12#",
                "010-0000-0000", AdminRole.CS_ADMIN
        );

        admin = new Admin("채원", "test@test.com",
                "Rlacodnjs12#", "010-0000-0000", AdminRole.CS_ADMIN);

        loginRequest = new LoginRequest("test@test.com", "Rlacodnjs12#");
    }

    @Test
    @DisplayName("정상 회원가입")
    void signup() {
        // given
        when(adminRepository.existsByEmail(request.email()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.password()))
                .thenReturn("passwordHashed");

        admin = new Admin(request.name(), request.email(), "passwordHashed",
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
        CreateAdminRequest request1 = new CreateAdminRequest(
                "채원", "test@test.com", "Rlacodnjs12#",
                "010-0000-0000", AdminRole.SUPER_ADMIN
        );

        // when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.create(request1));
        assertEquals("슈퍼관리자로 가입할 수 없습니다.", e.getMessage());
    }

    @Test
    @DisplayName("로그인 성공 케이스")
    void login() {
        // given
        admin.changeState(AdminState.ACTIVE);
        when(adminRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(loginRequest.password(), admin.getPassword())).thenReturn(true);

        // when
        Admin result = adminService.login(loginRequest);

        // then
        assertEquals(admin, result);
    }

    @Test
    @DisplayName("이메일이 일치하지 않을때 - 로그인 실패")
    void misMatchEmail() {
        // given
        when(adminRepository.findByEmail(loginRequest.email())).thenReturn(Optional.empty());

        // when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.login(loginRequest));
        assertEquals("이메일 또는 비밀번호가 일치하지 않습니다.", e.getMessage());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않을때 - 로그인 실패")
    void misMatchPassword() {
        // given
        when(adminRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(loginRequest.password(), admin.getPassword())).thenReturn(false);

        // when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.login(loginRequest));
        assertEquals("이메일 또는 비밀번호가 일치하지 않습니다.", e.getMessage());
    }

    @Test
    @DisplayName("비활성화 계정의 로그인 - 로그인 실패")
    void inactiveLogin() {
        // given
        admin.changeState(AdminState.INACTIVE);
        when(adminRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(loginRequest.password(), admin.getPassword())).thenReturn(true);

        // when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.login(loginRequest));
        assertEquals("비활성화된 계정입니다.", e.getMessage());
    }

    @Test
    @DisplayName("승인대기 계정의 로그인 - 로그인 실패")
    void pendingLogin() {
        // given
        admin.changeState(AdminState.PENDING);
        when(adminRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(loginRequest.password(), admin.getPassword())).thenReturn(true);

        // when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.login(loginRequest));
        assertEquals("승인대기 중인 계정입니다.", e.getMessage());
    }

    @Test
    @DisplayName("정지된 계정의 로그인 - 로그인 실패")
    void suspendedLogin() {
        // given
        admin.changeState(AdminState.SUSPENDED);
        when(adminRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(loginRequest.password(), admin.getPassword())).thenReturn(true);

        // when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.login(loginRequest));
        assertEquals("정지된 계정입니다.", e.getMessage());
    }

    @Test
    @DisplayName("거부된 계정의 로그인 - 로그인 실패")
    void deniedLogin() {
        // given
        admin.changeState(AdminState.DENIED);
        when(adminRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(loginRequest.password(), admin.getPassword())).thenReturn(true);

        // when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> adminService.login(loginRequest));
        assertEquals("거부된 계정입니다.", e.getMessage());
    }
}