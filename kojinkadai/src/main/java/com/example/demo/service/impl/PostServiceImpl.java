package com.example.demo.service.impl;

import com.example.demo.service.PostService;
import com.example.demo.domain.model.Post;
import com.example.demo.application.dto.PostWithUserDto;
import com.example.demo.application.usecase.PostUseCase;
import com.example.demo.application.usecase.UserProfileUseCase;
import com.example.demo.exception.BusinessException;
import com.example.demo.constants.AppConstants;
import com.example.demo.util.StringUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 投稿サービス実装クラス
 * 
 * 投稿管理に関するビジネスロジックを実装します。
 * 既存のUseCaseを活用しつつ、新しいビジネスルールを追加します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    
    private final PostUseCase postUseCase;
    private final UserProfileUseCase userProfileUseCase;
    
    @Override
    public Post createPost(String userId, String content) throws BusinessException {
        log.info("投稿作成開始: userId={}", userId);
        
        // 投稿内容のバリデーション
        validatePostContent(content);
        
        try {
            Post createdPost = postUseCase.createPost(userId, content);
            log.info("投稿作成完了: userId={}", userId);
            return createdPost;
            
        } catch (Exception e) {
            log.error("投稿作成でエラー: userId={}", userId, e);
            throw new BusinessException(
                "投稿の作成に失敗しました: " + e.getMessage(),
                "投稿の作成に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    public void updatePost(String postId, String content, String userId) throws BusinessException {
        log.info("投稿更新開始: postId={}, userId={}", postId, userId);
        
        // 投稿内容のバリデーション
        validatePostContent(content);
        
        try {
            postUseCase.updatePost(postId, content, userId);
            log.info("投稿更新完了: postId={}", postId);
            
        } catch (IllegalArgumentException e) {
            log.warn("投稿更新でエラー: postId={}, userId={}, error={}", postId, userId, e.getMessage());
            throw BusinessException.validationError(e.getMessage());
        } catch (Exception e) {
            log.error("投稿更新でエラー: postId={}, userId={}", postId, userId, e);
            throw new BusinessException(
                "投稿の更新に失敗しました: " + e.getMessage(),
                "投稿の更新に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    public void deletePost(String postId, String userId) throws BusinessException {
        log.info("投稿削除開始: postId={}, userId={}", postId, userId);
        
        try {
            postUseCase.deletePost(postId, userId);
            log.info("投稿削除完了: postId={}", postId);
            
        } catch (IllegalArgumentException e) {
            log.warn("投稿削除でエラー: postId={}, userId={}, error={}", postId, userId, e.getMessage());
            throw BusinessException.validationError(e.getMessage());
        } catch (Exception e) {
            log.error("投稿削除でエラー: postId={}, userId={}", postId, userId, e);
            throw new BusinessException(
                "投稿の削除に失敗しました: " + e.getMessage(),
                "投稿の削除に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Post getPostById(String postId) throws BusinessException {
        log.debug("投稿取得: postId={}", postId);
        
        try {
            return postUseCase.getPost(postId);
            
        } catch (IllegalArgumentException e) {
            log.warn("投稿取得でエラー: postId={}, error={}", postId, e.getMessage());
            throw BusinessException.notFound(e.getMessage());
        } catch (Exception e) {
            log.error("投稿取得でエラー: postId={}", postId, e);
            throw new BusinessException(
                "投稿の取得に失敗しました: " + e.getMessage(),
                "投稿の取得に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PostWithUserDto> getTimeline(String userId) throws BusinessException {
        log.debug("タイムライン取得: userId={}", userId);
        
        try {
            return postUseCase.getTimeline(userId);
            
        } catch (Exception e) {
            log.error("タイムライン取得でエラー: userId={}", userId, e);
            throw new BusinessException(
                "タイムラインの取得に失敗しました: " + e.getMessage(),
                "タイムラインの取得に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Post> getUserPosts(String userId) throws BusinessException {
        log.debug("ユーザー投稿取得: userId={}", userId);
        
        try {
            return userProfileUseCase.getUserPosts(userId);
            
        } catch (Exception e) {
            log.error("ユーザー投稿取得でエラー: userId={}", userId, e);
            throw new BusinessException(
                "投稿一覧の取得に失敗しました: " + e.getMessage(),
                "投稿一覧の取得に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isPostOwner(String postId, String userId) throws BusinessException {
        try {
            Post post = getPostById(postId);
            return post.getUserId().asString().equals(userId);
            
        } catch (BusinessException e) {
            if (e.getStatusCode() == 404) {
                // 投稿が見つからない場合は所有者ではない
                return false;
            }
            throw e;
        }
    }
    
    @Override
    public void validatePostContent(String content) throws BusinessException {
        // null・空文字チェック
        if (StringUtil.isBlank(content)) {
            throw BusinessException.validationError("投稿内容は必須です");
        }
        
        // 文字数チェック
        String trimmedContent = content.trim();
        if (trimmedContent.length() < AppConstants.Post.CONTENT_MIN_LENGTH) {
            throw BusinessException.validationError(
                String.format("投稿内容は%d文字以上で入力してください", 
                             AppConstants.Post.CONTENT_MIN_LENGTH)
            );
        }
        
        if (trimmedContent.length() > AppConstants.Post.CONTENT_MAX_LENGTH) {
            throw BusinessException.validationError(
                String.format("投稿内容は%d文字以下で入力してください", 
                             AppConstants.Post.CONTENT_MAX_LENGTH)
            );
        }
        
        // 禁止文字・単語チェック（例）
        if (containsProhibitedContent(trimmedContent)) {
            throw BusinessException.validationError("投稿内容に不適切な表現が含まれています");
        }
    }
    
    /**
     * 禁止コンテンツが含まれているかチェック
     * 
     * @param content 投稿内容
     * @return 禁止コンテンツが含まれている場合true
     */
    private boolean containsProhibitedContent(String content) {
        // 簡単な例として、特定のキーワードをチェック
        String[] prohibitedWords = {
            "spam", "advertisement", "広告"
        };
        
        String lowerContent = content.toLowerCase();
        for (String word : prohibitedWords) {
            if (lowerContent.contains(word.toLowerCase())) {
                return true;
            }
        }
        
        return false;
    }
}