package com.example.commercebackoffice.domain.admin.dto;

import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record CreateAdminRequest (

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 30, message = "최대 30자까지만 입력가능합니다.")
        String name,

        @Email(message = "올바른 이메일 형식을 입력해주세요.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~@#$%^&+=!])(?=\\S+$).{8,15}$"
        , message = "비밀번호는 영문 대/소문자와 숫자, 특수문자를 1개 이상 포함한 8-15자입니다.")
        String password,

        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$",
        message = "올바른 휴대폰 번호 형식이 아닙니다. (예: 010-0000-0000)")
        String phoneNumber,

        @NotNull(message = "관리자 역할을 입력해주세요.")
        AdminRole role
){
}
