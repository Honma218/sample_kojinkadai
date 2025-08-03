package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * ユーザー名バリデーター実装クラス
 * 
 * @ValidUsernameアノテーションの実際のバリデーション処理を実装します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");
    
    /** 予約語リスト */
    private static final Set<String> RESERVED_WORDS = Set.of(
        "admin", "administrator", "root", "system", "guest", "public", "private",
        "user", "users", "api", "www", "mail", "email", "support", "help",
        "info", "contact", "about", "login", "register", "signup", "profile",
        "settings", "config", "test", "demo", "null", "undefined"
    );
    
    private int minLength;
    private int maxLength;
    
    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
    }
    
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            updateErrorMessage(context, "ユーザー名は必須です");
            return false;
        }
        
        // 文字数チェック
        if (username.length() < minLength || username.length() > maxLength) {
            updateErrorMessage(context, String.format("ユーザー名は%d文字以上%d文字以下で入力してください", minLength, maxLength));
            return false;
        }
        
        // 文字種チェック（英数字とアンダースコアのみ）
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            updateErrorMessage(context, "ユーザー名は英数字とアンダースコアのみ使用できます");
            return false;
        }
        
        // 先頭文字チェック（数字やアンダースコアで始まらない）
        if (Character.isDigit(username.charAt(0)) || username.charAt(0) == '_') {
            updateErrorMessage(context, "ユーザー名は英字で始まる必要があります");
            return false;
        }
        
        // 予約語チェック
        if (RESERVED_WORDS.contains(username.toLowerCase())) {
            updateErrorMessage(context, "このユーザー名は使用できません");
            return false;
        }
        
        return true;
    }
    
    /**
     * エラーメッセージを更新
     * 
     * @param context バリデーションコンテキスト
     * @param message エラーメッセージ
     */
    private void updateErrorMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}