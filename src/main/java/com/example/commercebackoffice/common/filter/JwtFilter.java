package com.example.commercebackoffice.common.filter;

import com.example.commercebackoffice.common.security.AdminPrincipal;
import com.example.commercebackoffice.common.utils.JwtUtil;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

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
                antPathMatcher.match("/v3/api-docs/**", requestURL) || antPathMatcher.match("/swagger-ui.html/**", requestURL)
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

        // 인증 사용자 정보 생성 커스텀
        AdminPrincipal principal = new AdminPrincipal(adminId, adminEmail, adminRole);

        // Spring Security가 이해할 수 있는 권한 객체 생성
        var authorities = List.of(new SimpleGrantedAuthority(adminRole.getRole()));

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

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
