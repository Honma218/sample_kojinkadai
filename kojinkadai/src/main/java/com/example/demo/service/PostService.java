package com.example.demo.service;

import com.example.demo.domain.model.Post;
import com.example.demo.application.dto.PostWithUserDto;
import com.example.demo.exception.BusinessException;

import java.util.List;

/**
 * 投稿サービスインターfaces
 * 
 * 投稿管理に関するビジネスロジックを定義します。
 * 投稿の作成、編集、削除、タイムライン表示などの機能を提供します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public interface PostService {
    
    /**
     * 新規投稿を作成
     * 
     * @param userId 投稿者のユーザーID
     * @param content 投稿内容
     * @return 作成された投稿
     * @throws BusinessException 投稿作成に失敗した場合
     */
    Post createPost(String userId, String content) throws BusinessException;
    
    /**
     * 投稿を更新
     * 
     * @param postId 投稿ID
     * @param content 新しい投稿内容
     * @param userId 更新実行者のユーザーID
     * @throws BusinessException 投稿が見つからない、または権限がない場合
     */
    void updatePost(String postId, String content, String userId) throws BusinessException;
    
    /**
     * 投稿を削除
     * 
     * @param postId 投稿ID
     * @param userId 削除実行者のユーザーID
     * @throws BusinessException 投稿が見つからない、または権限がない場合
     */
    void deletePost(String postId, String userId) throws BusinessException;
    
    /**
     * 投稿IDで投稿を取得
     * 
     * @param postId 投稿ID
     * @return 投稿情報
     * @throws BusinessException 投稿が見つからない場合
     */
    Post getPostById(String postId) throws BusinessException;
    
    /**
     * ユーザーのタイムラインを取得
     * フォローしているユーザーと自分の投稿を時系列で表示
     * 
     * @param userId ユーザーID
     * @return タイムライン投稿リスト
     * @throws BusinessException タイムライン取得に失敗した場合
     */
    List<PostWithUserDto> getTimeline(String userId) throws BusinessException;
    
    /**
     * 指定ユーザーの投稿一覧を取得
     * 
     * @param userId ユーザーID
     * @return 投稿リスト
     * @throws BusinessException 投稿取得に失敗した場合
     */
    List<Post> getUserPosts(String userId) throws BusinessException;
    
    /**
     * 投稿の所有者確認
     * 
     * @param postId 投稿ID
     * @param userId ユーザーID
     * @return 所有者の場合true
     * @throws BusinessException 投稿が見つからない場合
     */
    boolean isPostOwner(String postId, String userId) throws BusinessException;
    
    /**
     * 投稿内容のバリデーション
     * 
     * @param content 投稿内容
     * @throws BusinessException バリデーションエラーの場合
     */
    void validatePostContent(String content) throws BusinessException;
}