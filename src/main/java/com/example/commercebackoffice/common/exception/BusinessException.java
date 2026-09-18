package com.example.commercebackoffice.common.exception;

import com.example.commercebackoffice.common.global.ResponseCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
