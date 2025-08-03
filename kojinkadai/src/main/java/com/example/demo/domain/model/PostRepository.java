package com.example.demo.domain.model;

import java.util.List;
import java.util.Optional;

/**
 * PostRepository インターフェイス
 * ----------------------------------
 * 投稿（Post）に関するデータ操作を抽象化するためのリポジトリ。
 * 実装はインフラ層で行い、データベースへの保存・検索処理の「仕様」だけをここで定義する。
 */
public interface PostRepository {
	/**
     * 投稿IDを指定して1件の投稿を取得する。
     *
     * @param id 投稿ID
     * @return 該当する投稿が存在すれば Optional<Post>、存在しなければ空
     */
    Optional<Post> findById(String id);
    
    /**
     * 特定のユーザーが投稿した全投稿を取得する。
     *
     * @param userId ユーザーID
     * @return 投稿のリスト
     */
    List<Post> findByUserId(String userId);
    
    /**
     * 登録されているすべての投稿を取得する。
     *
     * @return 投稿全件のリスト
     */
    List<Post> findAll();
 
    /**
     * タイムライン用に、複数ユーザー（フォロー中など）の投稿をまとめて取得する。
     *
     * @param userIds 投稿を取得したいユーザーIDのリスト
     * @return 該当ユーザーたちの投稿のリスト（時系列でソートされることが多い）
     */
    List<Post> findTimelineByUserIds(List<String> userIds);
    
    /**
     * 投稿を保存または更新する。
     *
     * @param post 保存したい投稿
     * @return 保存された投稿オブジェクト
     */
    Post save(Post post);
    /**
     * 投稿IDを指定して投稿を削除する。
     *
     * @param id 削除したい投稿のID
     */
    void deleteById(String id);
}