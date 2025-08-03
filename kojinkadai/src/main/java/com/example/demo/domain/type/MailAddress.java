package com.example.demo.domain.type;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class MailAddress {
  private final String value;

  public static MailAddress from(String mailAddress) {
    return new MailAddress(mailAddress);
  }

  @Override
  public String toString() {
    if(Objects.isNull(value)) {
      return "";
    }
    return value;
  }
}

