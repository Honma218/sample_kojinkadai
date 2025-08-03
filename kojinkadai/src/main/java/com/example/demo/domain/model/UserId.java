package com.example.demo.domain.model;

import lombok.Getter;


@Getter
public class UserId {
  private Object value; // String
  public UserId() {
    this.value = null;
  }
  private UserId(Object value) {
    this.value = value;
  }



//  public static UserId from(String userName) {
//    return new UserId(userName);
//  }

  public static UserId from(String id) {
    return new UserId(id);
  }

  public String asString() {
    if (value instanceof String) {
      return (String) value;
    }
    if (value instanceof Number) {
      return String.valueOf(value);
    }
    throw new IllegalStateException("UserId is not a String or Number: " + value);
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  /**
   * fnv132ハッシュによる変換
   * @return fnv132ハッシュされた文字列
   */
  public String toHash() {
    final int FNV_32_PRIME = 0x01000193;
    int hval = 0x811c9dc5;

    byte[] bytes = String.valueOf(value).getBytes();

    int size = bytes.length;

    for (byte aByte : bytes) {
      hval *= FNV_32_PRIME;
      hval ^= aByte;
    }
    return Integer.toHexString(hval);
  }
}


