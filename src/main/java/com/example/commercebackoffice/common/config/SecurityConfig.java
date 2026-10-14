package com.example.commercebackoffice.common.config;

import com.example.commercebackoffice.common.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, SecurityContextHolderAwareRequestFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/admins/signup", "/swagger-ui.html/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/admins/me/**", "/dashboard/**", "/auth/logout").authenticated()
                        .requestMatchers("/orders/**").hasAnyRole("CS_ADMIN", "OPERATION_ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/products/**", "/reviews/**").hasAnyRole("OPERATION_ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/**").hasRole("SUPER_ADMIN")
                ).build();
    }
}
