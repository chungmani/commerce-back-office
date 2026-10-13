package com.example.commercebackoffice.common.filter;

import com.example.commercebackoffice.common.utils.JwtUtil;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 토큰 발급받는 로그인 요청은 토큰 검사 안함
        String requestURL = request.getRequestURI();

        if (requestURL.equals("/auth/login") || requestURL.equals("/admins/signup")
                || antPathMatcher.match("/swagger-ui/**", requestURL) ||
                antPathMatcher.match("/v3/api-docs/**", requestURL)
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 여부 검사
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            sendUnauthorizedResponse(response);
            return;
        }

        // 토큰 유효성 검사
        if (!authorizationHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response);
            return;
        }

        String jwt = authorizationHeader.substring(7);
        if (jwt.isBlank() || !jwtUtil.validateToken(jwt)) {
            sendUnauthorizedResponse(response);
            return;
        }

        // 토큰 정보 확인
        String adminEmail = jwtUtil.extractEmail(jwt);
        Long adminId = jwtUtil.extractId(jwt);
        AdminRole adminRole = jwtUtil.extractRole(jwt);
        request.setAttribute("adminEmail", adminEmail);
        request.setAttribute("adminId", adminId);
        request.setAttribute("adminRole", adminRole);
        filterChain.doFilter(request, response);
    }


    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                "{\"success\":false,\"message\":\"유효하지 않거나 만료된 JWT 토큰입니다.\"}"
        );
    }
}
