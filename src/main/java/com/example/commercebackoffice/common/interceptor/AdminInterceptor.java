package com.example.commercebackoffice.common.interceptor;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.auth.dto.SessionAdmin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 현재 요청의 HttpSession에서 loginAdmin을 꺼내서 존재하는지 확인한다.
         SessionAdmin sessionAdmin = (SessionAdmin) request.getSession().getAttribute("loginAdmin");

        if (sessionAdmin == null) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }

        // 2. 권한도 검사한다.
        if (sessionAdmin.role() != AdminRole.SUPER_ADMIN) {
            throw new BusinessException(ResponseCode.FORBIDDEN_ADMIN);
        }
        return true;
    }
}
