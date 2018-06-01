package com.todarch.um.domain.kernel;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EncryptedPasswordTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldNotAcceptNullValue() {
    thrown.expect(IllegalArgumentException.class);
    EncryptedPassword.from(null);
  }

  @Test
  public void shouldImplementContentEquality() {
    String encryptedPassword = "encryptedPassword";
    EncryptedPassword ep1 = EncryptedPassword.from(encryptedPassword);
    EncryptedPassword ep2 = EncryptedPassword.from(encryptedPassword);
    Assertions.assertThat(ep1).isEqualTo(ep2);
  }
}
