package com.todarch.um;

import com.todarch.um.application.user.UserService;
import com.todarch.um.application.user.model.RegistrationCommand;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.helper.TestUser;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;

/**
 * Contains functionality to trigger message publishing for messaging tests.
 *
 * @author selimssevgi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmApplication.class)
@AutoConfigureMessageVerifier
public abstract class BaseContractTestMessaging {

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  // is used on triggeredBy
  protected void userRegistered() {
    User mockedUser = new User(TestUser.EMAIL, TestUser.ENCRYPTED_PASSWORD);
    Mockito.doReturn(mockedUser).when(userRepository).save(any());

    var registrationCommand =
        new RegistrationCommand(TestUser.EMAIL, TestUser.RAW_PASSWORD);

    userService.register(registrationCommand);
  }
}
