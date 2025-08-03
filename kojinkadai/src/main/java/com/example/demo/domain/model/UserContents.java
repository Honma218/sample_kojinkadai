package com.example.demo.domain.model;



import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.example.demo.domain.type.Content;
import com.example.demo.domain.type.DateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserContents {
  private final List<UserContent> values;

  public static UserContents from(List<UserContent> contents) {
    if(CollectionUtils.isEmpty(contents)) return new UserContents(Collections.emptyList());
    return new UserContents(contents);
  }

  @AllArgsConstructor
  @Getter
  public static class UserContent {
    private final int id;
    private final UserId userId;
    private final Content content;
    private final DateTime dateTime;

    public static UserContent from(
        int id,
        String userId,
        String content,
        LocalDateTime dateTime) {
      return new UserContent(
          id,
          UserId.from(userId),
          Content.from(content),
          DateTime.from(dateTime)
      );
    }
  }
}

