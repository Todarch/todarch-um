package com.todarch.um.domain.kernel;

import com.todarch.um.domain.ValueObject;
import lombok.EqualsAndHashCode;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@EqualsAndHashCode
public class EncryptedPassword implements ValueObject {
  private final String value;

  private EncryptedPassword(String value) {
    this.value = value;
  }

  public static EncryptedPassword from(String encryptedValue) {
    checkArgument(encryptedValue != null, "Encrypted Password cannot be null.");
    return new EncryptedPassword(encryptedValue);
  }

  public String value() {
    return value;
  }
}
