package com.example.demo.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Follow;
import com.example.demo.domain.model.FollowRepository;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowUseCase {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(String followerId, String followingId) {
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("自分自身をフォローすることはできません");
        }

        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new IllegalArgumentException("既にフォローしています");
        }

        Follow follow = Follow.create(followerId, followingId);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(String followerId, String followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
            .orElseThrow(() -> new IllegalArgumentException("フォロー関係が存在しません"));
        
        followRepository.delete(follow);
    }

    public List<User> getFollowingUsers(String userId) {
        List<String> followingIds = followRepository.findFollowingIdsByFollowerId(userId);
        return followingIds.stream()
            .map(id -> userRepository.findById(id).orElse(null))
            .filter(user -> user != null)
            .toList();
    }

    public List<User> getFollowerUsers(String userId) {
        List<Follow> follows = followRepository.findByFollowingId(userId);
        return follows.stream()
            .map(follow -> userRepository.findById(follow.getFollowerId().asString()).orElse(null))
            .filter(user -> user != null)
            .toList();
    }

    public boolean isFollowing(String followerId, String followingId) {
        return followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }
}