package com.example.demo.domain.model;

import java.util.List;
import java.util.Optional;

/**
 * FollowRepository インターフェイス
 * ----------------------------------
 * ユーザーのフォロー関係（Follow）を管理するためのデータアクセス操作を定義する。
 * 実装はSpring Data JPAなどの仕組みを使って提供されることが多い。
 */
public interface FollowRepository {
	/**
     * 特定のフォロワーとフォロー対象ユーザーの関係を取得。
     *
     * @param followerId フォローする側のユーザーID
     * @param followingId フォローされる側のユーザーID
     * @return 該当するFollowがあれば Optional で返す
     */
    Optional<Follow> findByFollowerIdAndFollowingId(String followerId, String followingId);
    
    /**
     * 指定されたユーザーがフォローしているユーザーのリストを取得。
     *
     * @param followerId フォローしているユーザーのID
     * @return Follow（フォロー関係）オブジェクトのリスト
     */
    List<Follow> findByFollowerId(String followerId);
    
    /**
     * 指定されたユーザーをフォローしているユーザーのリストを取得。
     *
     * @param followingId フォローされているユーザーのID
     * @return Follow（フォロー関係）オブジェクトのリスト
     */
    List<Follow> findByFollowingId(String followingId);
    
    /**
     * 指定ユーザーがフォローしているユーザーID一覧を取得。
     * タイムラインや投稿表示の対象ユーザー絞り込みなどに使う。
     *
     * @param followerId フォロワーのユーザーID
     * @return フォローしている相手のユーザーIDリスト
     */
    List<String> findFollowingIdsByFollowerId(String followerId);
    
    /**
     * 指定ユーザーがフォローしている人数をカウント。
     *
     * @param followerId ユーザーID
     * @return フォロー中の人数
     */
    int countByFollowerId(String followerId);
    
    /**
     * 指定ユーザーがフォローされている人数をカウント。
     *
     * @param followingId ユーザーID
     * @return フォロワー数
     */
    int countByFollowingId(String followingId);
    
    /**
     * フォロー関係を保存（フォローの追加など）。
     *
     * @param follow Follow オブジェクト
     * @return 保存された Follow
     */
    Follow save(Follow follow);
    
    /**
     * 指定のフォロー関係を削除（フォロー解除）。
     *
     * @param follow 削除したい Follow オブジェクト
     */
    void delete(Follow follow);
    /**
     * 指定のフォロー関係がすでに存在するかを判定。
     *
     * @param followerId フォローする側
     * @param followingId フォローされる側
     * @return すでにフォローしていれば true、していなければ false
     */
    boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);
}