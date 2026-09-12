package com.example.commercebackoffice.common.exception;

import com.example.commercebackoffice.common.global.ApiResponse;
import com.example.commercebackoffice.common.global.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> businessExceptionHandler(BusinessException e) {
        ResponseCode responseCode = e.getResponseCode();

        return ResponseEntity.status(responseCode.getStatus())
                .body(ApiResponse.fail(responseCode));
    }
}
