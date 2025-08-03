package com.example.demo.application.dto;

import com.example.demo.domain.model.UserContent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserContentDto {
  private final String userId;
  private final String content;

  public static UserContentDto from(
      UserContent userContent) {
    return new UserContentDto(
        userContent.getUserId().toString(),
        userContent.getContent().toString()
    );
  }
}
