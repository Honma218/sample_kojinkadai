package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ユーザー名バリデーションアノテーション
 * 
 * ユーザー名の形式をチェックするカスタムバリデーションです。
 * 英数字とアンダースコアのみ許可し、予約語をチェックします。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface ValidUsername {
    
    String message() default "ユーザー名は3文字以上20文字以下の英数字とアンダースコアのみ使用できます";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * 最小文字数
     */
    int minLength() default 3;
    
    /**
     * 最大文字数
     */
    int maxLength() default 20;
}