package com.example.demo.infrastructure.datasource;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDatasourceImpl implements UserRepository {
    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(String id) {
        return userMapper.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public List<User> searchByDisplayName(String keyword) {
        return userMapper.searchByDisplayName(keyword);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId().getValue() == null) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
        return user;
    }

    @Override
    public void deleteById(String id) {
        userMapper.deleteById(id);
    }
}