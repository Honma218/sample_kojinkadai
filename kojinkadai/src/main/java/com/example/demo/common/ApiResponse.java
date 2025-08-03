package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * API統一レスポンスクラス
 * 
 * RESTAPIのレスポンス形式を統一し、成功・失敗の両方に対応します。
 * クライアント側での処理を簡素化し、一貫性のあるAPIレスポンスを提供します。
 * 
 * @param <T> レスポンスデータの型
 * @author Generated with Claude Code
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    /** 処理成功フラグ */
    private boolean success;
    
    /** レスポンスメッセージ */
    private String message;
    
    /** レスポンスデータ */
    private T data;
    
    /** エラー詳細情報 */
    private ErrorDetails error;
    
    /** タイムスタンプ */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    
    /**
     * 成功レスポンスを生成
     * 
     * @param data レスポンスデータ
     * @param <T> データの型
     * @return 成功レスポンス
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("処理が正常に完了しました")
                .data(data)
                .build();
    }
    
    /**
     * 成功レスポンスを生成（メッセージ付き）
     * 
     * @param data レスポンスデータ
     * @param message 成功メッセージ
     * @param <T> データの型
     * @return 成功レスポンス
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
    
    /**
     * 失敗レスポンスを生成
     * 
     * @param message エラーメッセージ
     * @param <T> データの型
     * @return 失敗レスポンス
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(ErrorDetails.builder()
                        .code("GENERAL_ERROR")
                        .message(message)
                        .build())
                .build();
    }
    
    /**
     * 失敗レスポンスを生成（詳細情報付き）
     * 
     * @param message エラーメッセージ
     * @param errorCode エラーコード
     * @param <T> データの型
     * @return 失敗レスポンス
     */
    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(ErrorDetails.builder()
                        .code(errorCode)
                        .message(message)
                        .build())
                .build();
    }
    
    /**
     * バリデーションエラーレスポンスを生成
     * 
     * @param message エラーメッセージ
     * @param validationErrors バリデーションエラー詳細
     * @param <T> データの型
     * @return バリデーションエラーレスポンス
     */
    public static <T> ApiResponse<T> validationError(String message, Object validationErrors) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(ErrorDetails.builder()
                        .code("VALIDATION_ERROR")
                        .message(message)
                        .details(validationErrors)
                        .build())
                .build();
    }
    
    /**
     * エラー詳細情報クラス
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetails {
        /** エラーコード */
        private String code;
        
        /** エラーメッセージ */
        private String message;
        
        /** エラー詳細情報 */
        private Object details;
    }
}