package com.example.demo.domain.model;

import java.time.LocalDateTime;

import com.example.demo.domain.type.Content;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class Post {
    
    public Post() {
        // MyBatis用のデフォルトコンストラクタ
        this.id = null;
        this.userId = null;
        this.content = null;
        this.createdAt = null;
        this.updatedAt = null;
    }
    
    public Post(String id, String userId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = UserId.from(userId);
        this.content = Content.from(content);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // MyBatis用のsetterメソッド
    public void setUserId(String userId) {
        this.userId = UserId.from(userId);
    }
    
    public void setContent(String content) {
        this.content = Content.from(content);
    }
    
    private String id;
    private UserId userId;
    private Content content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Post from(String id, String userId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Post(
            id,
            userId,
            content,
            createdAt,
            updatedAt
        );
    }

    public static Post create(String userId, String content) {
        LocalDateTime now = LocalDateTime.now();
        return new Post(
            null, // IDは自動採番
            userId,
            content,
            now,
            now
        );
    }

    public Post update(String content) {
        return new Post(
            this.id,
            this.userId.asString(),
            content,
            this.createdAt,
            LocalDateTime.now()
        );
    }
}
