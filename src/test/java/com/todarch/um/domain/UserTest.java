package com.todarch.um.domain;

import com.todarch.um.helper.TestUser;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserTest {

  @Test
  public void shouldBeInitializedWithActivationCode() {
    var user = new User(TestUser.EMAIL, TestUser.ENCRYPTED_PASSWORD);
    Assertions.assertThat(user.activationCode()).isNotNull();
  }

  @Test
  public void shouldBeActivated() {
    var user = TestUser.newInstance();
    user.activate();
    Assertions.assertThat(user.isActivated()).isTrue();
  }
}
