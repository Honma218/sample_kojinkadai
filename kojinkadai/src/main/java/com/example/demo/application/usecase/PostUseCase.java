package com.example.demo.application.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.application.dto.PostWithUserDto;
import com.example.demo.domain.model.FollowRepository;
import com.example.demo.domain.model.Post;
import com.example.demo.domain.model.PostRepository;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostUseCase {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<PostWithUserDto> getTimeline(String userId) {
        // 一時的に全投稿を表示（MyBatisエラー回避のため）
        List<Post> posts = postRepository.findAll();
        
        // 投稿とユーザー情報を組み合わせてDTOに変換
        return posts.stream()
            .map(post -> {
                User user = userRepository.findById(post.getUserId().asString())
                    .orElse(null);
                return PostWithUserDto.from(post, user);
            })
            .filter(dto -> dto.getUser() != null) // ユーザーが存在しない投稿は除外
            .toList();
    }

    @Transactional
    public Post createPost(String userId, String content) {
        Post post = Post.create(userId, content);
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(String postId, String content, String userId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("投稿が見つかりません"));
        
        // 投稿者本人のみ編集可能
        if (!post.getUserId().asString().equals(userId)) {
            throw new IllegalArgumentException("自分の投稿のみ編集できます");
        }
        
        Post updatedPost = post.update(content);
        return postRepository.save(updatedPost);
    }

    @Transactional
    public void deletePost(String postId, String userId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("投稿が見つかりません"));
        
        // 投稿者本人のみ削除可能
        if (!post.getUserId().asString().equals(userId)) {
            throw new IllegalArgumentException("自分の投稿のみ削除できます");
        }
        
        postRepository.deleteById(postId);
    }

    public Post getPost(String postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("投稿が見つかりません"));
    }
}