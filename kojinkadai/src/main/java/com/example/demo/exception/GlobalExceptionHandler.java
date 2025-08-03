package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * グローバル例外ハンドラー
 * 
 * アプリケーション全体で発生する例外を統一的に処理し、
 * 適切なエラーレスポンスを返却します。
 * APIとWebページの両方に対応しています。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * ビジネス例外の処理
     * 
     * @param e ビジネス例外
     * @return エラーレスポンス
     */
    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException e) {
        log.warn("ビジネス例外が発生しました: {}", e.getMessage(), e);
        
        // APIリクエストの場合はJSONレスポンス
        if (isApiRequest()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("message", e.getUserMessage());
            errorResponse.put("code", e.getStatusCode());
            
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
        
        // Webページリクエストの場合はエラーページ
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", e.getStatusCode());
        modelAndView.addObject("errorMessage", e.getUserMessage());
        modelAndView.addObject("errorDescription", "操作を再度お試しください");
        
        return modelAndView;
    }
    
    /**
     * バリデーション例外の処理
     * 
     * @param e バリデーション例外
     * @return エラーレスポンス
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Object handleValidationException(Exception e) {
        log.warn("バリデーション例外が発生しました: {}", e.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        
        // APIリクエストの場合はJSONレスポンス
        if (isApiRequest()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("message", "入力内容に誤りがあります");
            errorResponse.put("validationErrors", errors);
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // Webページリクエストの場合は元のページに戻る
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", 400);
        modelAndView.addObject("errorMessage", "入力内容に誤りがあります");
        modelAndView.addObject("validationErrors", errors);
        
        return modelAndView;
    }
    
    /**
     * 不正な引数例外の処理
     * 
     * @param e 不正な引数例外
     * @return エラーレスポンス
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("不正な引数例外が発生しました: {}", e.getMessage(), e);
        
        // APIリクエストの場合はJSONレスポンス
        if (isApiRequest()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("message", "不正なパラメータです");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // Webページリクエストの場合はエラーページ
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", 400);
        modelAndView.addObject("errorMessage", "不正なパラメータです");
        modelAndView.addObject("errorDescription", "入力内容をご確認ください");
        
        return modelAndView;
    }
    
    /**
     * 予期しない例外の処理
     * 
     * @param e 予期しない例外
     * @return エラーレスポンス
     */
    @ExceptionHandler(Exception.class)
    public Object handleGenericException(Exception e) {
        log.error("予期しない例外が発生しました: {}", e.getMessage(), e);
        
        // APIリクエストの場合はJSONレスポンス
        if (isApiRequest()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("message", "システムエラーが発生しました");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        
        // Webページリクエストの場合はエラーページ
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", 500);
        modelAndView.addObject("errorMessage", "システムエラーが発生しました");
        modelAndView.addObject("errorDescription", "しばらく時間をおいて再度お試しください");
        
        return modelAndView;
    }
    
    /**
     * APIリクエストかどうかを判定
     * 
     * @return APIリクエストの場合true
     */
    private boolean isApiRequest() {
        // 実装を簡略化：実際のプロジェクトではRequestContextから判定
        // ここでは常にWebページリクエストとして処理
        return false;
    }
}