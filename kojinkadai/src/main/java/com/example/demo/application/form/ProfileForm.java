package com.example.demo.application.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ProfileForm {
    @Size(max=50, message = "表示名は50文字以内で入力してください")
    @NotBlank(message = "表示名は必須です")
    private String displayName;

    @Size(max=200, message = "自己紹介は200文字以内で入力してください")
    private String bio;
}