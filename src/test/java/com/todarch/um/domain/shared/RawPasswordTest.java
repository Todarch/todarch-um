package com.todarch.um.domain.shared;

import com.google.common.base.Strings;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.containsString;

@RunWith(JUnit4.class)
public class RawPasswordTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldNotAcceptNullValues() {
    thrown.expect(IllegalArgumentException.class);
    RawPassword.from(null);
  }

  @Test
  public void shouldConformMinLength() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(containsString(String.valueOf(RawPassword.MIN_LENGTH)));
    RawPassword.from("12");
  }

  @Test
  public void shouldConformMaxLength() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(containsString(String.valueOf(RawPassword.MAX_LENGTH)));
    RawPassword.from(Strings.repeat("a", RawPassword.MAX_LENGTH + 1));
  }

  @Test
  public void givenValidInputShouldReturnValidConstructedObject() {
    String validPass = "1r45thc9";
    RawPassword rawPassword = RawPassword.from(validPass);
    Assertions.assertThat(rawPassword).isNotNull();
    Assertions.assertThat(rawPassword.value()).isEqualTo(validPass);
  }

  @Test
  public void shouldImplementContentEquality() {
    RawPassword rawPassword1 = RawPassword.from("1r45thc9");
    RawPassword rawPassword2 = RawPassword.from("1r45thc9");
    Assertions.assertThat(rawPassword1).isEqualTo(rawPassword2);
  }

}

