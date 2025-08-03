package com.example.demo.infrastructure.datasource;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.application.dto.UserContentDto;
import com.example.demo.application.dto.UserContentReadDto;

@Mapper
public interface UserContentMapper {
  void insert(@Param("dto") UserContentDto dto);
  List<UserContentReadDto> select();
  List<UserContentReadDto> selectById(@Param("userId") String userId);
}