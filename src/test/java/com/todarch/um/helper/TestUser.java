package com.todarch.um.helper;

import com.todarch.um.domain.User;
import com.todarch.um.domain.kernel.EncryptedPassword;
import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.RawPassword;

public final class TestUser {

  private TestUser() {
    throw new AssertionError("Cannot create a object from utility class");
  }

  public static final Email EMAIL = Email.from("test@user.com");
  public static final Email REGISTERED_EMAIL = Email.from("registered@user.com");
  public static final Email INACTIVE_EMAIL = Email.from("inactive@user.com");
  public static final Email NONEXISTENT_EMAIL = Email.from("nonexistent@user.com");
  public static final RawPassword RAW_PASSWORD = RawPassword.from("12345678");
  public static final RawPassword WRONG_RAW_PASSWORD = RawPassword.from("wrongpassword");
  public static final EncryptedPassword ENCRYPTED_PASSWORD =
      EncryptedPassword.from("12345678");

  /**
   * The entity itself is mutable, cannot have a static final field for multiple tests.
   */
  public static User newInstance() {
    return new User(EMAIL, ENCRYPTED_PASSWORD);
  }

}
