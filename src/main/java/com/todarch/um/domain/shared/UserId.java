package com.todarch.um.domain.shared;

import com.todarch.um.domain.ValueObject;
import lombok.NonNull;

/**
 * UserId to wrap id value for user entity.
 *
 * @deprecated keeping it here just in case later usage
 */
@Deprecated
public class UserId implements ValueObject {
  private final String value;

  private UserId(String value) {
    this.value = value.toUpperCase();
  }

  public static UserId from(@NonNull String uuid) {
    return new UserId(uuid);
  }

  public String value() {
    return value;
  }
}

