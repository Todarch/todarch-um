package com.todarch.um.domain.shared;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Email Value Object.
 */
public class Email {

  //TODO:selimssevgi: strict ones are too long. find a better one later.
  private static final String EMAIL_REGEX = "\\S+@\\S+\\.\\S+";
  private String value;

  private Email(String email) {
    this.value = email;
  }

  /**
   * Static factory method.
   *
   * @param email valid value value
   * @return valid constructed object
   * @throws IllegalArgumentException when value is null, or does satisfy the regex.
   */
  public static Email from(String email) {
    checkArgument(email != null, "Email cannot be null");
    checkArgument(email.matches(EMAIL_REGEX), "Invalid value format");

    return new Email(email);
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

    Email email1 = (Email) o;
    return Objects.equals(value, email1.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value();
  }
}

