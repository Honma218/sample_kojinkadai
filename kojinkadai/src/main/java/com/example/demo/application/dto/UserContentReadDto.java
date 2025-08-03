package com.example.demo.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.domain.model.UserContents;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserContentReadDto {
  private final int id;
  private final String userId;
  private final String content;
  private final LocalDateTime createdAt;
  /**
   * RestAPIのレスポンス用変換
   * @param contents
   * @return
   */
  public static List<UserContentReadDto> from(UserContents contents) {
    return contents.getValues().stream().map(
        content -> new UserContentReadDto(
            content.getId(),
            content.getUserId().toString(),
            content.getContent().toString(),
            LocalDateTime.parse(content.getDateTime().toString())
        )
    ).collect(Collectors.toUnmodifiableList());
  }
}

