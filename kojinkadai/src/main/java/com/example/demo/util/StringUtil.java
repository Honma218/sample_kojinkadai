package com.example.demo.util;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * 文字列ユーティリティクラス
 * 
 * 文字列の操作、バリデーション、フォーマット処理を提供します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public final class StringUtil {
    
    /** HTMLタグ除去用パターン */
    private static final Pattern HTML_TAG_PATTERN = Pattern.compile("<[^>]+>");
    
    /** 改行文字パターン */
    private static final Pattern NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
    
    /** 複数の空白文字パターン */
    private static final Pattern MULTIPLE_SPACES_PATTERN = Pattern.compile("\\s+");
    
    /** プライベートコンストラクタ（インスタンス化を防ぐ） */
    private StringUtil() {
        throw new UnsupportedOperationException("ユーティリティクラスはインスタンス化できません");
    }
    
    /**
     * 文字列が空かnullかを判定
     * 
     * @param str 判定対象の文字列
     * @return 空またはnullの場合true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * 文字列が空白文字のみかnullかを判定
     * 
     * @param str 判定対象の文字列
     * @return 空白文字のみまたはnullの場合true
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * 文字列が空でなくnullでもないかを判定
     * 
     * @param str 判定対象の文字列
     * @return 有効な文字列の場合true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * 文字列が空白文字以外を含むかを判定
     * 
     * @param str 判定対象の文字列
     * @return 有効な文字列の場合true
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    /**
     * 文字列をトリムし、nullの場合は空文字を返す
     * 
     * @param str 対象文字列
     * @return トリム済み文字列
     */
    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }
    
    /**
     * 文字列をトリムし、空の場合はnullを返す
     * 
     * @param str 対象文字列
     * @return トリム済み文字列またはnull
     */
    public static String trimToNull(String str) {
        String trimmed = trimToEmpty(str);
        return trimmed.isEmpty() ? null : trimmed;
    }
    
    /**
     * 文字列を指定した長さで切り詰める
     * 
     * @param str 対象文字列
     * @param maxLength 最大長
     * @return 切り詰め済み文字列
     */
    public static String truncate(String str, int maxLength) {
        if (isEmpty(str) || maxLength < 0) {
            return str;
        }
        return str.length() <= maxLength ? str : str.substring(0, maxLength);
    }
    
    /**
     * 文字列を指定した長さで切り詰め、末尾に省略記号を付ける
     * 
     * @param str 対象文字列
     * @param maxLength 最大長（省略記号を含む）
     * @return 切り詰め済み文字列
     */
    public static String truncateWithEllipsis(String str, int maxLength) {
        if (isEmpty(str) || maxLength < 4) {
            return str;
        }
        
        if (str.length() <= maxLength) {
            return str;
        }
        
        return str.substring(0, maxLength - 3) + "...";
    }
    
    /**
     * HTMLタグを除去
     * 
     * @param str 対象文字列
     * @return HTMLタグ除去済み文字列
     */
    public static String stripHtml(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return HTML_TAG_PATTERN.matcher(str).replaceAll("");
    }
    
    /**
     * 改行文字を指定した文字列に置換
     * 
     * @param str 対象文字列
     * @param replacement 置換文字列
     * @return 置換済み文字列
     */
    public static String replaceNewlines(String str, String replacement) {
        if (isEmpty(str)) {
            return str;
        }
        return NEWLINE_PATTERN.matcher(str).replaceAll(replacement);
    }
    
    /**
     * 複数の連続する空白文字を単一の空白に置換
     * 
     * @param str 対象文字列
     * @return 置換済み文字列
     */
    public static String normalizeSpaces(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return MULTIPLE_SPACES_PATTERN.matcher(str.trim()).replaceAll(" ");
    }
    
    /**
     * 文字列をキャメルケースに変換
     * 
     * @param str 対象文字列
     * @return キャメルケース文字列
     */
    public static String toCamelCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        String[] words = str.toLowerCase().split("[\\s_-]+");
        StringBuilder result = new StringBuilder(words[0]);
        
        for (int i = 1; i < words.length; i++) {
            if (isNotEmpty(words[i])) {
                result.append(Character.toUpperCase(words[i].charAt(0)))
                      .append(words[i].substring(1));
            }
        }
        
        return result.toString();
    }
    
    /**
     * 文字列をスネークケースに変換
     * 
     * @param str 対象文字列
     * @return スネークケース文字列
     */
    public static String toSnakeCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        return str.replaceAll("([a-z])([A-Z])", "$1_$2")
                  .replaceAll("[\\s-]+", "_")
                  .toLowerCase();
    }
    
    /**
     * コレクションを指定した区切り文字で結合
     * 
     * @param collection 結合対象のコレクション
     * @param delimiter 区切り文字
     * @return 結合済み文字列
     */
    public static String join(Collection<?> collection, String delimiter) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        boolean first = true;
        
        for (Object item : collection) {
            if (!first) {
                result.append(delimiter);
            }
            result.append(item);
            first = false;
        }
        
        return result.toString();
    }
    
    /**
     * 配列を指定した区切り文字で結合
     * 
     * @param array 結合対象の配列
     * @param delimiter 区切り文字
     * @return 結合済み文字列
     */
    public static String join(Object[] array, String delimiter) {
        if (array == null || array.length == 0) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                result.append(delimiter);
            }
            result.append(array[i]);
        }
        
        return result.toString();
    }
    
    /**
     * 文字列がメールアドレスの形式かを判定
     * 
     * @param email 判定対象の文字列
     * @return メールアドレス形式の場合true
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        
        Pattern emailPattern = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        );
        
        return emailPattern.matcher(email).matches();
    }
    
    /**
     * 文字列がURLの形式かを判定
     * 
     * @param url 判定対象の文字列
     * @return URL形式の場合true
     */
    public static boolean isValidUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }
        
        Pattern urlPattern = Pattern.compile(
            "^https?://[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,})+(?:/.*)?$"
        );
        
        return urlPattern.matcher(url).matches();
    }
}