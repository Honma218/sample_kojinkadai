package com.example.demo.domain.model;

public interface UserContentRepository {
	  void save(UserContent userContent);
	  UserContents select();
	  UserContents select(UserId userId);
	}
