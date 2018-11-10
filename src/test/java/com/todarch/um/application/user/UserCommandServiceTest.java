package com.todarch.um.application.user;

import com.todarch.um.application.user.model.RegistrationCommand;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.helper.BaseServiceTest;
import com.todarch.um.helper.TestUser;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

public class UserCommandServiceTest extends BaseServiceTest {

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private UserCommandService userCommandService;

  @Test
  public void shouldNotRegisterSameEmail() {
    Mockito.doReturn(Optional.of(TestUser.newInstance()))
        .when(userRepository).findByEmail(TestUser.EMAIL);

    var cmd =
        new RegistrationCommand(TestUser.EMAIL, TestUser.RAW_PASSWORD);
    try {
      userCommandService.register(cmd);
      Assertions.fail("Should not register same email again");
    } catch (EmailAddressAlreadyInUse ex) {
      // ignore
    }

  }

  @Test
  public void shouldThrowExceptionOnInvalidActivationCode() {
    String invalidActivationCode = "invalidActivationCode";
    Mockito.doReturn(Optional.empty())
        .when(userRepository).findByActivationCode(invalidActivationCode);

    try {
      userCommandService.activateAccount(invalidActivationCode);
      Assertions.fail("Should fail on invalid activation code");
    } catch (InvalidActivationCode ex) {
      // ignore
    }
  }
}
