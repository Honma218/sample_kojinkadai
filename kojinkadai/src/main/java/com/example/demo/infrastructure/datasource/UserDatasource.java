package com.example.demo.infrastructure.datasource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

import com.example.demo.application.auth.UserAuthRepository;
import com.google.common.collect.ImmutableSet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserDatasource implements UserAuthRepository {
  private final UserDetailsManager manager;
  private final PasswordEncoder passwordEncoder;

  public void createUser(String userName, String password) {
    User user = new User(
        userName,
        passwordEncoder.encode(password),
        ImmutableSet.of(new SimpleGrantedAuthority("ROLE_USER"))
    );
    manager.createUser(user);
  }
}
