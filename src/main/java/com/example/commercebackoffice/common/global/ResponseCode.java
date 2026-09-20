package com.example.commercebackoffice.common.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    // 공통
    OK(HttpStatus.OK, "COMMON_01", "요청이 성공했습니다."),
    CREATED(HttpStatus.CREATED, "COMMON_02", "생성되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON_03", "요청이 성공했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_04", "잘못된 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_05", "접근할 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_06", "권한이 없습니다."),

    // admin
    SUPER_ADMIN_SIGNUP_NOT_ALLOWED(HttpStatus.FORBIDDEN, "ADMIN_01", "슈퍼 관리자로는 회원가입할 수 없습니다."),
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, "ADMIN_02", "이메일 또는 비밀번호가 일치하지 않습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "ADMIN_03", "이메일 또는 비밀번호가 일치하지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "ADMIN_04", "중복된 이메일입니다."),
    ADMIN_LOGIN_NOT_ALLOWED(HttpStatus.FORBIDDEN, "ADMIN_05", "로그인 할 수 없는 계정입니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "ADMIN_06", "권한이 없습니다."),
    NOT_PENDING_ADMIN(HttpStatus.BAD_REQUEST, "ADMIN_07", "승인대기인 관리자가 아닙니다."),
    INVALID_PASSWORD(HttpStatus.CONFLICT, "ADMIN_08", "비밀번호가 일치하지 않습니다."),
    DUPLICATED_PASSWORD(HttpStatus.CONFLICT, "ADMIN_09", "동일한 비밀번호로 설정할 수 없습니다.");



    private final HttpStatus status;
    private final String code;
    private final String message;

}
