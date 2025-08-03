package com.example.demo.application.dto;

import java.time.LocalDateTime;

import com.example.demo.domain.model.Post;
import com.example.demo.domain.model.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostWithUserDto {
    private final String postId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final User user;

    public static PostWithUserDto from(Post post, User user) {
        return new PostWithUserDto(
            post.getId(),
            post.getContent().getValue(),
            post.getCreatedAt(),
            post.getUpdatedAt(),
            user
        );
    }
}