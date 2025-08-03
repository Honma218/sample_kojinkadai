package com.example.demo.infrastructure.datasource;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.model.User;

@Mapper
public interface UserMapper {
    Optional<User> findById(@Param("id") String id);
    Optional<User> findByUsername(@Param("username") String username);
    List<User> findAll();
    List<User> searchByDisplayName(@Param("keyword") String keyword);
    void insert(User user);
    void update(User user);
    void deleteById(@Param("id") String id);
}