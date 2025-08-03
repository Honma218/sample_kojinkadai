package com.example.demo.infrastructure.datasource;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.Post;
import com.example.demo.domain.model.PostRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostDatasourceImpl implements PostRepository {
    private final PostMapper postMapper;

    @Override
    public Optional<Post> findById(String id) {
        return postMapper.findById(id);
    }

    @Override
    public List<Post> findByUserId(String userId) {
        return postMapper.findByUserId(userId);
    }

    @Override
    public List<Post> findAll() {
        return postMapper.findAll();
    }

    @Override
    public List<Post> findTimelineByUserIds(List<String> userIds) {
        return postMapper.findTimelineByUserIds(userIds);
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == null) {
            postMapper.insert(post);
        } else {
            postMapper.update(post);
        }
        return post;
    }

    @Override
    public void deleteById(String id) {
        postMapper.deleteById(id);
    }
}