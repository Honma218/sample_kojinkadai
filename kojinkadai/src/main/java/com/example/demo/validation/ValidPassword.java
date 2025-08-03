package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * パスワードバリデーションアノテーション
 * 
 * パスワードの強度をチェックするカスタムバリデーションです。
 * 最低文字数、文字種の組み合わせなどをチェックします。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    
    String message() default "パスワードは6文字以上で、英数字を含む必要があります";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * 最小文字数
     */
    int minLength() default 6;
    
    /**
     * 最大文字数
     */
    int maxLength() default 100;
    
    /**
     * 英字を必須とするか
     */
    boolean requireAlphabet() default true;
    
    /**
     * 数字を必須とするか
     */
    boolean requireDigit() default true;
    
    /**
     * 特殊文字を必須とするか
     */
    boolean requireSpecialChar() default false;
}