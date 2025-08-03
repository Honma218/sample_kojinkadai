package com.example.demo.infrastructure.datasource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.application.dto.UserContentDto;
import com.example.demo.application.dto.UserContentReadDto;
import com.example.demo.domain.model.UserContent;
import com.example.demo.domain.model.UserContentRepository;
import com.example.demo.domain.model.UserContents;
import com.example.demo.domain.model.UserId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserContentDatasource implements UserContentRepository {
  private final UserContentMapper mapper;

  @Override
  public void save(UserContent userContent) {
    mapper.insert(UserContentDto.from(userContent));
  }

  @Override
  public UserContents select() {
    List<UserContentReadDto> dtos = mapper.select();
    return convert(dtos);
  }

  @Override
  public UserContents select(UserId userId) {
    List<UserContentReadDto> dtos = mapper.selectById(userId.toString());
    return convert(dtos);
  }
  
  private UserContents convert(List<UserContentReadDto> dtos) {
    return UserContents.from(
        dtos.stream().map( dto -> UserContents.UserContent.from(
            dto.getId(),
            dto.getUserId(),
            dto.getContent(),
            dto.getCreatedAt()
        )).collect(Collectors.toUnmodifiableList())
    );
  }
}
