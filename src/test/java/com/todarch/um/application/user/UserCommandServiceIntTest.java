package com.todarch.um.application.user;

import com.todarch.um.application.user.model.RegistrationCommand;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.helper.BaseIntTest;
import com.todarch.um.helper.TestUser;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Testing user command service with other components.
 *
 * @author selimssevgi
 */
public class UserCommandServiceIntTest extends BaseIntTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserCommandService userCommandService;

  /**
   * Would like to test the database behavior too.
   */
  @Test
  public void shouldRegisterUser() {
    var cmd = new RegistrationCommand(TestUser.EMAIL, TestUser.RAW_PASSWORD);

    userCommandService.register(cmd);

    User user = userRepository.findByEmail(TestUser.EMAIL).orElse(null);
    Assertions.assertThat(user.isActivated()).isFalse();
  }

  @Test
  public void shouldActivateAccount() {
    User testUser = dbHelper.createTestUser();

    userCommandService.activateAccount(testUser.activationCode());

    User user = userRepository.findByEmail(testUser.email()).orElse(null);
    Assertions.assertThat(user.isActivated()).isTrue();
  }
}
