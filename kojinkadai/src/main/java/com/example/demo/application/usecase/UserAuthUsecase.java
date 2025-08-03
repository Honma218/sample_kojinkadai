package com.example.demo.application.usecase;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.application.auth.UserAuthRepository;
import com.example.demo.application.form.UserForm;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthUsecase {
    private final UserAuthRepository authRepository;
    private final UserRepository userRepository;

    @Transactional
    public void userCreate(UserForm form, HttpServletRequest request) throws ServletException {
        // バリデーション
        if (!form.isPasswordMatching()) {
            throw new IllegalArgumentException("パスワードが一致しません");
        }

        // ユーザー名の重複チェック
        if (userRepository.findByUsername(form.getUsername()).isPresent()) {
            throw new IllegalArgumentException("ユーザー名は既に使用されています");
        }

        // Spring Security用ユーザー作成
        authRepository.createUser(form.getUsername(), form.getPassword());

        // アプリケーション用ユーザープロフィール作成
        User user = User.create(
            form.getUsername(),
            form.getEmail(),
            form.getDisplayName(),
            form.getBio()
        );
        userRepository.save(user);

        // 自動ログイン
        request.login(form.getUsername(), form.getPassword());
    }
}
