package com.todarch.um.domain.shared;

import com.todarch.um.domain.ValueObject;
import com.todarch.um.domain.kernel.EncryptedPassword;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * RawPassword Value Object.
 */
public class RawPassword implements ValueObject {
  protected static final int MAX_LENGTH = 20;
  protected static final int MIN_LENGTH = 8;
  private final String value;

  private RawPassword(String rawPassword) {
    this.value = rawPassword;
  }

  /**
   * Static factory method.
   *
   * @param rawPassword raw password value
   * @return valid constructed object
   * @throws IllegalArgumentException when value is null, and does not conform length limits.
   */
  public static RawPassword from(String rawPassword) {
    checkArgument(rawPassword != null, "RawPassword cannot be null.");
    checkArgument(rawPassword.length() >= MIN_LENGTH,
        "RawPassword cannot be shorter than %s", MIN_LENGTH);
    checkArgument(rawPassword.length() <= MAX_LENGTH,
        "RawPassword cannot be longer than %s", MAX_LENGTH);

    return new RawPassword(rawPassword);
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RawPassword that = (RawPassword) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public EncryptedPassword encryptWith(PasswordEncoder passwordEncoder) {
    return EncryptedPassword.from(passwordEncoder.encode(value()));
  }
}

