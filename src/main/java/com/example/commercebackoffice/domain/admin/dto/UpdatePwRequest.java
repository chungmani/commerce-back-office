package com.example.commercebackoffice.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePwRequest(

        @NotBlank(message = "현재 비밀번호를 입력해주세요.")
        String currentPassword,

        @NotBlank(message = "새 비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~@#$%^&+=!])(?=\\S+$).{8,15}$"
                , message = "비밀번호는 영문 대/소문자와 숫자, 특수문자를 1개 이상 포함한 8-15자입니다.")
        String newPassword
) {
}
