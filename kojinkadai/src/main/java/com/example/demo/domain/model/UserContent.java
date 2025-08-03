package com.example.demo.domain.model;

import com.example.demo.domain.type.MailAddress;
import com.example.demo.domain.type.Content;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserContent {
	private final UserId userId;
	private final Content content;

  public static UserContent from(String userId, String content) {
    return new UserContent(
        UserId.from(userId),
        Content.from(content)
    );
  }
}

