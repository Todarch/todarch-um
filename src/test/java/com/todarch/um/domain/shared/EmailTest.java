package com.todarch.um.domain.shared;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EmailTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldNotAcceptNullValue() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(Matchers.containsString("null"));
    Email.from(null);
  }

  @Test
  public void shouldConformEmailRegex() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(Matchers.containsString("format"));
    Email.from("invalidEmail");
  }

  @Test
  public void givenValidInputShouldReturnValidConstructedObject() {
    String validEmail = "se@gmail.com";
    Email email = Email.from(validEmail);
    Assertions.assertThat(email).isNotNull();
    Assertions.assertThat(email.value()).isEqualTo(validEmail);
  }

  @Test
  public void shouldImplementContentEquality() {
    String validEmail = "se@gmail.com";
    Email email1 = Email.from(validEmail);
    Email email2 = Email.from(validEmail);
    Assertions.assertThat(email1).isEqualTo(email2);
  }

  @Test
  public void shouldOverrideToString() {
    String validEmail = "se@gmail.com";
    Email email = Email.from(validEmail);
    Assertions.assertThat(email.value()).isEqualTo(email.toString());
  }

}

