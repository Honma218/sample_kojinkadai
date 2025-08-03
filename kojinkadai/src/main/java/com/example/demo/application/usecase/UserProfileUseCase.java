package com.example.demo.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.FollowRepository;
import com.example.demo.domain.model.Post;
import com.example.demo.domain.model.PostRepository;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザープロフィール関連のユースケースを提供するサービスクラス。
 * プロフィールの取得、投稿の取得、フォロー・フォロワー数の取得、
 * およびプロフィール更新のロジックを持つ。
 */
@Service
@RequiredArgsConstructor
public class UserProfileUseCase {
	// ユーザー情報の永続化に関する処理を行うリポジトリ
    private final UserRepository userRepository;
    // 投稿情報の永続化に関する処理を行うリポジトリ
    private final PostRepository postRepository;
    // フォロー情報の永続化に関する処理を行うリポジトリ
    private final FollowRepository followRepository;


    /**
     * ユーザーIDからユーザー情報を取得する。
     * @param userId ユーザーID
     * @return ユーザー情報
     */
    public User getUserProfile(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません"));
    }

    /**
     * ユーザー名からユーザー情報を取得する。
     * @param username ユーザー名
     * @return ユーザー情報
     */
    public User getUserProfileByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません"));
    }

    /**
     * 特定ユーザーの投稿一覧を取得する。
     * @param userId ユーザーID
     * @return 投稿リスト
     */

    public List<Post> getUserPosts(String userId) {
        return postRepository.findByUserId(userId);
    }


    /**
     * ユーザーがフォローしている人数を取得する。
     * @param userId ユーザーID
     * @return フォロー中の人数
     */
    public int getFollowingCount(String userId) {
        return followRepository.countByFollowerId(userId);
    }

    /**
     * ユーザーをフォローしている人数を取得する。
     * @param userId ユーザーID
     * @return フォロワーの人数
     */
    public int getFollowerCount(String userId) {
        return followRepository.countByFollowingId(userId);
    }

    /**
     * プロフィールを更新する（表示名・自己紹介の編集）。
     * @param userId ユーザーID
     * @param displayName 新しい表示名
     * @param bio 新しい自己紹介文
     * @return 更新後のユーザーオブジェクト
     */
    @Transactional
    public User updateProfile(String userId, String displayName, String bio) {
    	// ユーザー情報を取得
        User user = getUserProfile(userId);
        // ユーザー情報を更新（アイコンはそのまま）
        User updatedUser = user.updateProfile(displayName, bio, user.getAvatarUrl());
        // DBに保存して返す
        return userRepository.save(updatedUser);
    }
}