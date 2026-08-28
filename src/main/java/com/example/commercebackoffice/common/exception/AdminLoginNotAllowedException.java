package com.example.commercebackoffice.common.exception;

public class AdminLoginNotAllowedException extends BusinessException{
    public AdminLoginNotAllowedException(String message) {
        super(message);
    }
}
