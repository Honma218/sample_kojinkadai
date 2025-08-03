package com.example.demo.infrastructure.datasource;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.Follow;
import com.example.demo.domain.model.FollowRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowDatasourceImpl implements FollowRepository {
    private final FollowMapper followMapper;

    @Override
    public Optional<Follow> findByFollowerIdAndFollowingId(String followerId, String followingId) {
        return followMapper.findByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Override
    public List<Follow> findByFollowerId(String followerId) {
        return followMapper.findByFollowerId(followerId);
    }

    @Override
    public List<Follow> findByFollowingId(String followingId) {
        return followMapper.findByFollowingId(followingId);
    }

    @Override
    public List<String> findFollowingIdsByFollowerId(String followerId) {
        return followMapper.findFollowingIdsByFollowerId(followerId);
    }

    @Override
    public int countByFollowerId(String followerId) {
        return followMapper.countByFollowerId(followerId);
    }

    @Override
    public int countByFollowingId(String followingId) {
        return followMapper.countByFollowingId(followingId);
    }

    @Override
    public Follow save(Follow follow) {
        followMapper.insert(follow);
        return follow;
    }

    @Override
    public void delete(Follow follow) {
        followMapper.delete(follow.getFollowerId().asString(), follow.getFollowingId().asString());
    }

    @Override
    public boolean existsByFollowerIdAndFollowingId(String followerId, String followingId) {
        return followMapper.existsByFollowerIdAndFollowingId(followerId, followingId);
    }
}