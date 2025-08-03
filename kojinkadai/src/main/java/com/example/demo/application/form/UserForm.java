package com.example.demo.application.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm {
    @Size(max=50, message = "ユーザー名は50文字以内で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "ユーザー名は英数字とアンダースコアのみ使用できます")
    @NotBlank(message = "ユーザー名は必須です")
    private String username;

    @Size(min=6, max=64, message = "パスワードは6文字以上64文字以内で入力してください")
    @NotBlank(message = "パスワードは必須です")
    private String password;

    @Size(min=6, max=64, message = "確認用パスワードは6文字以上64文字以内で入力してください")
    @NotBlank(message = "確認用パスワードは必須です")
    private String passwordConfirm;

    @Email(message = "正しいメールアドレスを入力してください")
    @NotBlank(message = "メールアドレスは必須です")
    private String email;

    @Size(max=50, message = "表示名は50文字以内で入力してください")
    @NotBlank(message = "表示名は必須です")
    private String displayName;

    @Size(max=200, message = "自己紹介は200文字以内で入力してください")
    private String bio;

    public boolean isPasswordMatching() {
        return password != null && password.equals(passwordConfirm);
    }
}
