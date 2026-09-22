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
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "COMMON_07", "중복된 이메일입니다."),

    // admin
    SUPER_ADMIN_SIGNUP_NOT_ALLOWED(HttpStatus.FORBIDDEN, "ADMIN_01", "슈퍼 관리자로는 회원가입할 수 없습니다."),
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, "ADMIN_02", "이메일 또는 비밀번호가 일치하지 않습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "ADMIN_03", "이메일 또는 비밀번호가 일치하지 않습니다."),
    ADMIN_LOGIN_NOT_ALLOWED(HttpStatus.FORBIDDEN, "ADMIN_04", "로그인 할 수 없는 계정입니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "ADMIN_05", "권한이 없습니다."),
    NOT_PENDING_ADMIN(HttpStatus.BAD_REQUEST, "ADMIN_06", "승인대기인 관리자가 아닙니다."),
    INVALID_PASSWORD(HttpStatus.CONFLICT, "ADMIN_07", "비밀번호가 일치하지 않습니다."),
    DUPLICATED_PASSWORD(HttpStatus.CONFLICT, "ADMIN_08", "동일한 비밀번호로 설정할 수 없습니다."),

    // customer
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "CUSTOMER_01", "고객을 찾을 수 없습니다."),

    // product
    PRODUCT_INVALID_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_01", "재고는 0개 이상이어야 합니다."),
    PRODUCT_INVALID_PRICE(HttpStatus.BAD_REQUEST, "PRODUCT_02", "가격은 0원 이상이어야 합니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODCUT_03", "상품을 찾을 수 없습니다."),
    PRODUCT_INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_04", "상품의 재고가 부족합니다."),
    PRODUCT_INVALID_STOCK_QUANTITY(HttpStatus.BAD_REQUEST, "PRODUCT_05", "재고 변경 수량은 1개 이상이어야 합니다."),

    // order
    ORDER_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "ORDER_01", "수량은 1이상이어야 합니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_02", "해당 주문을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

}
