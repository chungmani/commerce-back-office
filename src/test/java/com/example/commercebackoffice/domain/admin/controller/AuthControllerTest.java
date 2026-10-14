package com.example.commercebackoffice.domain.admin.controller;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.auth.controller.AuthController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("로그인된 관리자가 로그아웃")
    void logout() {
        // given
        MockHttpSession session = new MockHttpSession();
        Admin admin = new Admin("채원", "test@test.com",
                "Rlacodnjs12#", "010-0000-0000", AdminRole.CS_ADMIN);

        // when
        ResponseEntity<Void> response = authController.logout();

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(session.isInvalid());
    }

    @Test
    @DisplayName("로그인 하지 않은 관리자가 로그아웃")
    void nullLogout() {
        // given
        MockHttpSession session = new MockHttpSession();

        // when
        ResponseEntity<Void> response = authController.logout();

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}