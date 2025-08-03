package com.example.demo.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * フォロー関係を表すドメインモデル
 * 
 * ユーザー間のフォロー関係を管理し、フォローするユーザー（follower）と
 * フォローされるユーザー（following）の関係、およびフォロー開始日時を保持します。
 * MyBatisとの互換性のため、フィールドは非non-finalで、デフォルトコンストラクタを提供しています。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Follow {
    /** フォロー関係の一意識別子 */
    private String id;
    
    /** フォローするユーザーのID */
    private UserId followerId;
    
    /** フォローされるユーザーのID */
    private UserId followingId;
    
    /** フォロー関係が作成された日時 */
    private LocalDateTime createdAt;

    /**
     * データベースから取得したデータからFollowインスタンスを作成
     * 
     * @param id フォロー関係ID
     * @param followerId フォローするユーザーのID文字列
     * @param followingId フォローされるユーザーのID文字列
     * @param createdAt 作成日時
     * @return Followインスタンス
     */
    public static Follow from(String id, String followerId, String followingId, LocalDateTime createdAt) {
        return new Follow(
            id,
            UserId.from(followerId),
            UserId.from(followingId),
            createdAt
        );
    }

    /**
     * 新しいフォロー関係を作成
     * IDはデータベースで自動採番され、作成日時は現在時刻が設定されます。
     * 
     * @param followerId フォローするユーザーのID文字列
     * @param followingId フォローされるユーザーのID文字列
     * @return 新しいFollowインスタンス
     */
    public static Follow create(String followerId, String followingId) {
        return new Follow(
            null, // IDはデータベースで自動採番
            UserId.from(followerId),
            UserId.from(followingId),
            LocalDateTime.now()
        );
    }
}