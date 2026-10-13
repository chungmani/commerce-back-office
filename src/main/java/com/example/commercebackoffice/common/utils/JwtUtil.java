package com.example.commercebackoffice.common.utils;

import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 기본 세팅
    public static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 24 * 60 * 60 * 1000L;

    private SecretKey key;
    private JwtParser parser;

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    // 어플리케이션 실행 될때 가장 먼저 실행되게 하는 어노테이션
    @PostConstruct
    public void init() {
        byte[] bytes = Decoders.BASE64.decode(secretKeyString);
        this.key = Keys.hmacShaKeyFor(bytes);
        this.parser = Jwts.parser()
                .verifyWith(this.key)
                .build();
    }

    // 토큰 발행
    public String generateToken(String email, Long adminId, AdminRole role) {
        Date now = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .claim("email", email)
                .claim("adminId", adminId)
                .claim("role", role)
                .issuedAt(now)  // 발급 시간
                .expiration(new Date(now.getTime() + TOKEN_TIME)) // 만료시간
                .signWith(key, Jwts.SIG.HS256) // 토큰에 서명을 생성
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }
        try {
            parser.parseSignedClaims(token); // 만료 여부 검사
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    // 토큰 내용 꺼내기
    private Claims extractAllClaims(String token) {
        return parser.parseSignedClaims(token).getPayload();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public Long extractId(String token) {
        return extractAllClaims(token).get("adminId", Long.class);
    }

    public AdminRole extractRole(String token) {
        String adminRole = extractAllClaims(token).get("role", String.class);
        return AdminRole.valueOf(adminRole);
    }
}
