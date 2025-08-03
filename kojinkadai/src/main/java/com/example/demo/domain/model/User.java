package com.example.demo.domain.model;

import java.time.LocalDateTime;

import com.example.demo.domain.type.MailAddress;
import com.example.demo.domain.type.Name;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class User {
    
    public User() {
        // MyBatis用のデフォルトコンストラクタ
        this.id = null;
        this.username = null;
        this.email = null;
        this.displayName = null;
        this.bio = null;
        this.avatarUrl = null;
        this.createdAt = null;
        this.updatedAt = null;
    }
    
    public User(String id, String username, String email, String displayName, 
                String bio, String avatarUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id != null ? UserId.from(id) : null;
        this.username = username;
        this.email = MailAddress.from(email);
        this.displayName = Name.from(displayName);
        this.bio = bio;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // MyBatis用のsetterメソッド
    public void setEmail(String email) {
        this.email = MailAddress.from(email);
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = Name.from(displayName);
    }
    private UserId id;
    private String username;
    private MailAddress email;
    private Name displayName;
    private String bio;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static User from(String id, String username, String email, String displayName, 
                           String bio, String avatarUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new User(
            id,
            username,
            email,
            displayName,
            bio,
            avatarUrl,
            createdAt,
            updatedAt
        );
    }

    public static User create(String username, String email, String displayName, String bio) {
        LocalDateTime now = LocalDateTime.now();
        return new User(
            null, // IDは自動採番
            username,
            email,
            displayName,
            bio,
            null, // アバターは後で設定
            now,
            now
        );
    }

    public User updateProfile(String displayName, String bio, String avatarUrl) {
        return new User(
            this.id != null ? this.id.asString() : null,
            this.username,
            this.email.toString(),
            displayName,
            bio,
            avatarUrl,
            this.createdAt,
            LocalDateTime.now()
        );
    }
}
