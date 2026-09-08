package com.example.commercebackoffice.domain.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateAdminRequest {

    @Size(max = 30, message = "최대 30자까지만 입력가능합니다.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$",
            message = "올바른 휴대폰 번호 형식이 아닙니다. (예: 010-0000-0000)")
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phoneNumber;
}
