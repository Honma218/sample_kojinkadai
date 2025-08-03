package com.example.demo.constants;

/**
 * アプリケーション定数クラス
 * 
 * アプリケーション全体で使用する定数を定義します。
 * マジックナンバーやハードコードされた文字列を排除し、
 * 保守性を向上させます。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public final class AppConstants {
    
    /** プライベートコンストラクタ（インスタンス化を防ぐ） */
    private AppConstants() {
        throw new UnsupportedOperationException("定数クラスはインスタンス化できません");
    }
    
    /**
     * ページング関連定数
     */
    public static final class Paging {
        /** デフォルトページサイズ */
        public static final int DEFAULT_PAGE_SIZE = 20;
        
        /** 最大ページサイズ */
        public static final int MAX_PAGE_SIZE = 100;
        
        /** 最小ページサイズ */
        public static final int MIN_PAGE_SIZE = 1;
        
        /** デフォルトページ番号 */
        public static final int DEFAULT_PAGE_NUMBER = 0;
    }
    
    /**
     * ユーザー関連定数
     */
    public static final class User {
        /** ユーザー名最小文字数 */
        public static final int USERNAME_MIN_LENGTH = 3;
        
        /** ユーザー名最大文字数 */
        public static final int USERNAME_MAX_LENGTH = 20;
        
        /** 表示名最小文字数 */
        public static final int DISPLAY_NAME_MIN_LENGTH = 1;
        
        /** 表示名最大文字数 */
        public static final int DISPLAY_NAME_MAX_LENGTH = 50;
        
        /** パスワード最小文字数 */
        public static final int PASSWORD_MIN_LENGTH = 6;
        
        /** パスワード最大文字数 */
        public static final int PASSWORD_MAX_LENGTH = 100;
        
        /** 自己紹介最大文字数 */
        public static final int BIO_MAX_LENGTH = 500;
    }
    
    /**
     * 投稿関連定数
     */
    public static final class Post {
        /** 投稿内容最小文字数 */
        public static final int CONTENT_MIN_LENGTH = 1;
        
        /** 投稿内容最大文字数 */
        public static final int CONTENT_MAX_LENGTH = 1000;
        
        /** タイムライン取得デフォルト件数 */
        public static final int TIMELINE_DEFAULT_LIMIT = 50;
        
        /** ユーザー投稿取得デフォルト件数 */
        public static final int USER_POSTS_DEFAULT_LIMIT = 20;
    }
    
    /**
     * フォロー関連定数
     */
    public static final class Follow {
        /** フォロー一覧取得デフォルト件数 */
        public static final int FOLLOWING_DEFAULT_LIMIT = 50;
        
        /** フォロワー一覧取得デフォルト件数 */
        public static final int FOLLOWERS_DEFAULT_LIMIT = 50;
    }
    
    /**
     * 検索関連定数
     */
    public static final class Search {
        /** 検索キーワード最小文字数 */
        public static final int KEYWORD_MIN_LENGTH = 1;
        
        /** 検索キーワード最大文字数 */
        public static final int KEYWORD_MAX_LENGTH = 100;
        
        /** 検索結果デフォルト件数 */
        public static final int RESULTS_DEFAULT_LIMIT = 20;
    }
    
    /**
     * セキュリティ関連定数
     */
    public static final class Security {
        /** セッションタイムアウト（秒） */
        public static final int SESSION_TIMEOUT_SECONDS = 3600; // 1時間
        
        /** パスワードハッシュ化強度 */
        public static final int PASSWORD_HASH_STRENGTH = 12;
        
        /** CSRF保護対象外パス */
        public static final String[] CSRF_IGNORE_PATHS = {
            "/api/public/**",
            "/h2-console/**"
        };
    }
    
    /**
     * URL関連定数
     */
    public static final class Urls {
        /** ルートパス */
        public static final String ROOT = "/";
        
        /** ログインページ */
        public static final String LOGIN = "/user";
        
        /** ユーザー登録ページ */
        public static final String SIGNUP = "/user/signup";
        
        /** ユーザー登録完了ページ */
        public static final String SIGNUP_COMPLETE = "/user/signup-complete";
        
        /** トップページ（ボード） */
        public static final String BOARD = "/board";
        
        /** プロフィールページ */
        public static final String PROFILE = "/profile";
        
        /** 検索ページ */
        public static final String SEARCH = "/search";
        
        /** フォロー一覧ページ */
        public static final String FOLLOWING = "/follow/following";
        
        /** フォロワー一覧ページ */
        public static final String FOLLOWERS = "/follow/followers";
    }
    
    /**
     * メッセージ関連定数
     */
    public static final class Messages {
        /** 成功メッセージ */
        public static final String SUCCESS_GENERAL = "処理が正常に完了しました";
        public static final String SUCCESS_USER_CREATED = "ユーザー登録が完了しました";
        public static final String SUCCESS_PROFILE_UPDATED = "プロフィールを更新しました";
        public static final String SUCCESS_POST_CREATED = "投稿を作成しました";
        public static final String SUCCESS_POST_UPDATED = "投稿を更新しました";
        public static final String SUCCESS_POST_DELETED = "投稿を削除しました";
        public static final String SUCCESS_FOLLOWED = "フォローしました";
        public static final String SUCCESS_UNFOLLOWED = "フォローを解除しました";
        
        /** エラーメッセージ */
        public static final String ERROR_GENERAL = "エラーが発生しました";
        public static final String ERROR_UNAUTHORIZED = "認証が必要です";
        public static final String ERROR_FORBIDDEN = "アクセス権限がありません";
        public static final String ERROR_NOT_FOUND = "指定されたリソースが見つかりません";
        public static final String ERROR_VALIDATION = "入力内容に誤りがあります";
        public static final String ERROR_USERNAME_EXISTS = "このユーザー名は既に使用されています";
        public static final String ERROR_EMAIL_EXISTS = "このメールアドレスは既に使用されています";
        public static final String ERROR_INVALID_CREDENTIALS = "ユーザー名またはパスワードが正しくありません";
    }
    
    /**
     * 日時フォーマット定数
     */
    public static final class DateFormats {
        /** 日時表示フォーマット */
        public static final String DATETIME_DISPLAY = "yyyy-MM-dd HH:mm";
        
        /** 日付表示フォーマット */
        public static final String DATE_DISPLAY = "yyyy-MM-dd";
        
        /** 時刻表示フォーマット */
        public static final String TIME_DISPLAY = "HH:mm";
        
        /** ISO日時フォーマット */
        public static final String ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    }
}