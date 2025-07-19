package com.example.javaboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "유효한 이메일 형식이 아닙니다.")
    private final String username;

    private final String password;

    private final Integer age;

    public SignUpRequestDto(String username, String password, Integer age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
}
