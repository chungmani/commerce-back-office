package com.example.commercebackoffice.common.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(ResponseCode responseCode, T data) {
        return new ApiResponse<>(true, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode) {
        return new ApiResponse<>(
                false,
                responseCode.getCode(),
                responseCode.getMessage(),
                null
        );
    }
}
