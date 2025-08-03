package com.example.demo.util;

import com.example.demo.constants.AppConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日時ユーティリティクラス
 * 
 * 日時の計算、フォーマット、比較処理を提供します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public final class DateUtil {
    
    /** プライベートコンストラクタ（インスタンス化を防ぐ） */
    private DateUtil() {
        throw new UnsupportedOperationException("ユーティリティクラスはインスタンス化できません");
    }
    
    /**
     * 現在日時を取得
     * 
     * @return 現在日時
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
    
    /**
     * 日時を表示用フォーマットで文字列に変換
     * 
     * @param dateTime 変換対象の日時
     * @return フォーマット済み文字列
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(AppConstants.DateFormats.DATETIME_DISPLAY));
    }
    
    /**
     * 日付を表示用フォーマットで文字列に変換
     * 
     * @param dateTime 変換対象の日時
     * @return フォーマット済み日付文字列
     */
    public static String formatDateForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(AppConstants.DateFormats.DATE_DISPLAY));
    }
    
    /**
     * 時刻を表示用フォーマットで文字列に変換
     * 
     * @param dateTime 変換対象の日時
     * @return フォーマット済み時刻文字列
     */
    public static String formatTimeForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(AppConstants.DateFormats.TIME_DISPLAY));
    }
    
    /**
     * 相対時間を表示用文字列に変換
     * （例：「3分前」「2時間前」「1日前」）
     * 
     * @param dateTime 基準日時
     * @return 相対時間文字列
     */
    public static String formatRelativeTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        
        LocalDateTime now = LocalDateTime.now();
        
        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        if (minutes < 1) {
            return "たった今";
        } else if (minutes < 60) {
            return minutes + "分前";
        }
        
        long hours = ChronoUnit.HOURS.between(dateTime, now);
        if (hours < 24) {
            return hours + "時間前";
        }
        
        long days = ChronoUnit.DAYS.between(dateTime, now);
        if (days < 7) {
            return days + "日前";
        }
        
        long weeks = days / 7;
        if (weeks < 4) {
            return weeks + "週間前";
        }
        
        long months = days / 30;
        if (months < 12) {
            return months + "ヶ月前";
        }
        
        long years = days / 365;
        return years + "年前";
    }
    
    /**
     * 指定した日時が今日かどうかを判定
     * 
     * @param dateTime 判定対象の日時
     * @return 今日の場合true
     */
    public static boolean isToday(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return dateTime.toLocalDate().equals(now.toLocalDate());
    }
    
    /**
     * 指定した日時が昨日かどうかを判定
     * 
     * @param dateTime 判定対象の日時
     * @return 昨日の場合true
     */
    public static boolean isYesterday(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return dateTime.toLocalDate().equals(yesterday.toLocalDate());
    }
    
    /**
     * 指定した日時が過去かどうかを判定
     * 
     * @param dateTime 判定対象の日時
     * @return 過去の場合true
     */
    public static boolean isPast(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        return dateTime.isBefore(LocalDateTime.now());
    }
    
    /**
     * 指定した日時が未来かどうかを判定
     * 
     * @param dateTime 判定対象の日時
     * @return 未来の場合true
     */
    public static boolean isFuture(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        return dateTime.isAfter(LocalDateTime.now());
    }
    
    /**
     * 2つの日時の差を分単位で計算
     * 
     * @param from 開始日時
     * @param to 終了日時
     * @return 差（分）
     */
    public static long minutesBetween(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            return 0;
        }
        return ChronoUnit.MINUTES.between(from, to);
    }
    
    /**
     * 2つの日時の差を時間単位で計算
     * 
     * @param from 開始日時
     * @param to 終了日時
     * @return 差（時間）
     */
    public static long hoursBetween(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            return 0;
        }
        return ChronoUnit.HOURS.between(from, to);
    }
    
    /**
     * 2つの日時の差を日単位で計算
     * 
     * @param from 開始日時
     * @param to 終了日時
     * @return 差（日）
     */
    public static long daysBetween(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(from, to);
    }
}