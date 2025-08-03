package com.example.demo.util;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.domain.model.Post;
import com.example.demo.domain.model.User;

public class TestUserFactory {
    
    public static User createMockUser(String username, String email, String displayName, String bio) {
        return User.from(
            UUID.randomUUID().toString(),
            username,
            email,
            displayName,
            bio,
            null,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
    
    public static Post createMockPost(String userId, String content) {
        return Post.from(
            UUID.randomUUID().toString(),
            userId,
            content,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
}