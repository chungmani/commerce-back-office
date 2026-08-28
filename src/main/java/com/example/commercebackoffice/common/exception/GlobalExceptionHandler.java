package com.example.commercebackoffice.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SuperAdminSignupNotAllowedException.class)
    public ResponseEntity<String> handleSuperAdminSignupNotAllowed(
            SuperAdminSignupNotAllowedException e
    ) {
        log.error("[ERROR] 슈퍼어드민으로 회원가입 불가", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        log.error("[ERROR] 이미 존재하는 이메일", e );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> handleInvalidLogin(InvalidLoginException e) {
        log.error("[ERROR] InvalidLoginException 발생", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(AdminLoginNotAllowedException.class)
    public ResponseEntity<String> handleAdminLoginNotAllowed(AdminLoginNotAllowedException e) {
        log.error("[ERROR] AdminLoginNotAllowedException 발생", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }



}
