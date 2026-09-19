package com.example.commercebackoffice.common.interceptor;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.auth.dto.SessionAdmin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final PathMatcher pathMatcher;

    public AdminInterceptor(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 현재 요청의 HttpSession에서 loginAdmin을 꺼내서 존재하는지 확인한다.
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }

        SessionAdmin sessionAdmin = (SessionAdmin) session.getAttribute("loginAdmin");

        if (sessionAdmin == null) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }

        // 2. 요청이 admins/me 인가?
        if (pathMatcher.match("/admins/me", request.getRequestURI())) {
            return true;
        }


        // 3. 슈퍼 관리자인가?
        if (sessionAdmin.role() != AdminRole.SUPER_ADMIN) {
            throw new BusinessException(ResponseCode.FORBIDDEN_ADMIN);
        }
        return true;
    }
}
