package com.example.demo.infrastructure.datasource;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.model.Post;

@Mapper
public interface PostMapper {
    Optional<Post> findById(@Param("id") String id);
    List<Post> findByUserId(@Param("userId") String userId);
    List<Post> findAll();
    List<Post> findTimelineByUserIds(@Param("userIds") List<String> userIds);
    void insert(Post post);
    void update(Post post);
    void deleteById(@Param("id") String id);
}