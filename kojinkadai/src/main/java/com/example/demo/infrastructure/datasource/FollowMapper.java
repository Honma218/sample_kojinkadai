package com.example.demo.infrastructure.datasource;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.model.Follow;

@Mapper
public interface FollowMapper {
    Optional<Follow> findByFollowerIdAndFollowingId(@Param("followerId") String followerId, @Param("followingId") String followingId);
    List<Follow> findByFollowerId(@Param("followerId") String followerId);
    List<Follow> findByFollowingId(@Param("followingId") String followingId);
    List<String> findFollowingIdsByFollowerId(@Param("followerId") String followerId);
    int countByFollowerId(@Param("followerId") String followerId);
    int countByFollowingId(@Param("followingId") String followingId);
    void insert(Follow follow);
    void delete(@Param("followerId") String followerId, @Param("followingId") String followingId);
    boolean existsByFollowerIdAndFollowingId(@Param("followerId") String followerId, @Param("followingId") String followingId);
}