package com.example.demo.domain.type;

import java.util.Objects;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Name {
  private final String value;

  public static Name from(String name) {
    return new Name(name);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(String name) {
    return value.equals(name);
  }

  @Override
  public String toString() {
    if(Objects.isNull(this.value)) {
      return "名無しさん";
    }
      return value;
  }
}

