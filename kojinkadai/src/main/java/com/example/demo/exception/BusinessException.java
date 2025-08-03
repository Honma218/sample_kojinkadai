package com.example.demo.exception;

/**
 * ビジネスロジック例外クラス
 * 
 * アプリケーション固有のビジネスルール違反時に発生する例外を表します。
 * ユーザーに表示可能なメッセージを含み、適切なHTTPステータスコードを返却します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public class BusinessException extends RuntimeException {
    
    /** HTTPステータスコード */
    private final int statusCode;
    
    /** ユーザー表示用メッセージ */
    private final String userMessage;
    
    /**
     * デフォルトコンストラクタ（400 Bad Request）
     * 
     * @param message エラーメッセージ
     */
    public BusinessException(String message) {
        this(message, message, 400);
    }
    
    /**
     * ユーザーメッセージ付きコンストラクタ
     * 
     * @param message システム用エラーメッセージ
     * @param userMessage ユーザー表示用メッセージ
     */
    public BusinessException(String message, String userMessage) {
        this(message, userMessage, 400);
    }
    
    /**
     * フルコンストラクタ
     * 
     * @param message システム用エラーメッセージ
     * @param userMessage ユーザー表示用メッセージ
     * @param statusCode HTTPステータスコード
     */
    public BusinessException(String message, String userMessage, int statusCode) {
        super(message);
        this.userMessage = userMessage;
        this.statusCode = statusCode;
    }
    
    /**
     * 原因例外付きコンストラクタ
     * 
     * @param message エラーメッセージ
     * @param cause 原因例外
     */
    public BusinessException(String message, Throwable cause) {
        this(message, message, 400, cause);
    }
    
    /**
     * フル原因例外付きコンストラクタ
     * 
     * @param message システム用エラーメッセージ
     * @param userMessage ユーザー表示用メッセージ
     * @param statusCode HTTPステータスコード
     * @param cause 原因例外
     */
    public BusinessException(String message, String userMessage, int statusCode, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
        this.statusCode = statusCode;
    }
    
    /**
     * HTTPステータスコードを取得
     * 
     * @return HTTPステータスコード
     */
    public int getStatusCode() {
        return statusCode;
    }
    
    /**
     * ユーザー表示用メッセージを取得
     * 
     * @return ユーザー表示用メッセージ
     */
    public String getUserMessage() {
        return userMessage;
    }
    
    // 便利なファクトリーメソッド
    
    /**
     * 認証エラーを生成
     * 
     * @param message エラーメッセージ
     * @return 認証エラー例外
     */
    public static BusinessException unauthorized(String message) {
        return new BusinessException(message, "認証が必要です", 401);
    }
    
    /**
     * 権限エラーを生成
     * 
     * @param message エラーメッセージ
     * @return 権限エラー例外
     */
    public static BusinessException forbidden(String message) {
        return new BusinessException(message, "アクセス権限がありません", 403);
    }
    
    /**
     * リソース未発見エラーを生成
     * 
     * @param message エラーメッセージ
     * @return リソース未発見エラー例外
     */
    public static BusinessException notFound(String message) {
        return new BusinessException(message, "指定されたリソースが見つかりません", 404);
    }
    
    /**
     * バリデーションエラーを生成
     * 
     * @param message エラーメッセージ
     * @return バリデーションエラー例外
     */
    public static BusinessException validationError(String message) {
        return new BusinessException(message, "入力内容に誤りがあります", 400);
    }
}