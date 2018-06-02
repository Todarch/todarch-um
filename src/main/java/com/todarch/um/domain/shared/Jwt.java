package com.todarch.um.domain.shared;

import com.todarch.um.domain.ValueObject;
import lombok.NonNull;

public class Jwt implements ValueObject {
  private final String token;

  private Jwt(String token) {
    this.token = token;
  }

  public static Jwt from(@NonNull String token) {
    return new Jwt(token);
  }

  public String token() {
    return token;
  }

  @Override
  public String toString() {
    return token();
  }
}
