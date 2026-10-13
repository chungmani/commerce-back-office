package com.example.commercebackoffice.common.interceptor;

import com.example.commercebackoffice.common.exception.BusinessException;
import com.example.commercebackoffice.common.global.ResponseCode;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object value = request.getAttribute("adminRole");
        AdminRole adminRole = null;

        if (value instanceof AdminRole) {
           adminRole = (AdminRole) value;
        } else {
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }


        // 2. 요청이 admins/me 인가?
        if (antPathMatcher.match("/admins/me/**", request.getRequestURI())
                || antPathMatcher.match("/dashboard/**", request.getRequestURI())) {
            return true;
        }

        // 2-1 product 접근 가능한 관리자
        if (antPathMatcher.match("/products/**", request.getRequestURI()) && adminRole == AdminRole.OPERATION_ADMIN) {
            return true;
        }

        // 2-2 order 접근 가능한 관리자
        if (antPathMatcher.match("/orders/**", request.getRequestURI())
                && (adminRole == AdminRole.CS_ADMIN || adminRole == AdminRole.OPERATION_ADMIN)) {
            return true;
        }

        // 3. 슈퍼 관리자인가?
        if (adminRole != AdminRole.SUPER_ADMIN) {
            throw new BusinessException(ResponseCode.FORBIDDEN_ADMIN);
        }
        return true;
    }
}
