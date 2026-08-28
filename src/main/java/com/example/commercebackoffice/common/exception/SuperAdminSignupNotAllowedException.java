package com.example.commercebackoffice.common.exception;

public class SuperAdminSignupNotAllowedException extends BusinessException{
    public SuperAdminSignupNotAllowedException(String message) {
        super(message);
    }
}