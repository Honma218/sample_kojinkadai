package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * パスワードバリデーター実装クラス
 * 
 * @ValidPasswordアノテーションの実際のバリデーション処理を実装します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    
    private int minLength;
    private int maxLength;
    private boolean requireAlphabet;
    private boolean requireDigit;
    private boolean requireSpecialChar;
    
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
        this.requireAlphabet = constraintAnnotation.requireAlphabet();
        this.requireDigit = constraintAnnotation.requireDigit();
        this.requireSpecialChar = constraintAnnotation.requireSpecialChar();
    }
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        
        // 文字数チェック
        if (password.length() < minLength || password.length() > maxLength) {
            updateErrorMessage(context, String.format("パスワードは%d文字以上%d文字以下で入力してください", minLength, maxLength));
            return false;
        }
        
        // 英字チェック
        if (requireAlphabet && !Pattern.compile("[a-zA-Z]").matcher(password).find()) {
            updateErrorMessage(context, "パスワードに英字を含めてください");
            return false;
        }
        
        // 数字チェック
        if (requireDigit && !Pattern.compile("[0-9]").matcher(password).find()) {
            updateErrorMessage(context, "パスワードに数字を含めてください");
            return false;
        }
        
        // 特殊文字チェック
        if (requireSpecialChar && !Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]").matcher(password).find()) {
            updateErrorMessage(context, "パスワードに特殊文字を含めてください");
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