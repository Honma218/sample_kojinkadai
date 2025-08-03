package com.example.demo.domain.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DateTime {
  private final LocalDateTime value;
  private final DateTimeFormatter formatter =
      DateTimeFormatter.ISO_DATE_TIME;

  public static DateTime from(LocalDateTime dateTime) {
    return new DateTime(dateTime);
  }

  @Override
  public String toString() {
    return value.format(formatter);
  }
}
