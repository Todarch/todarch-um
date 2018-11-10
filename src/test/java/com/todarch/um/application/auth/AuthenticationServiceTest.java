package com.todarch.um.application.auth;

import com.todarch.um.application.auth.model.AuthCommand;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.helper.BaseServiceTest;
import com.todarch.um.helper.TestUser;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;

/**
 * Authentication service tests.
 *
 * @author selimssevgi
 */
public class AuthenticationServiceTest extends BaseServiceTest {

  @Autowired
  private AuthenticationService authenticationService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private AuthenticationManager authenticationManager;

  /**
   * Setup mock to handle correct and wrong password values.
   */
  @Before
  public void setUp() throws Exception {
    String rawPassword = TestUser.RAW_PASSWORD.value();
    String wrongPassword = TestUser.WRONG_RAW_PASSWORD.value();

    var usernamePasswordAuthToken =
        new UsernamePasswordAuthenticationToken(
            TestUser.EMAIL.value(),
            rawPassword,
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    Mockito.doReturn(usernamePasswordAuthToken)
        .when(authenticationManager)
        .authenticate(argThat(token -> rawPassword.equals(token.getCredentials())));

    Mockito.doThrow(new BadCredentialsException("bad credentials"))
        .when(authenticationManager)
        .authenticate(argThat(token -> wrongPassword.equals(token.getCredentials())));
  }

  @Test
  public void shouldThrowIncorrectCredentialsExceptionIfUserNotFound() {
    Mockito.doReturn(Optional.empty())
        .when(userRepository).findByEmail(TestUser.NONEXISTENT_EMAIL);

    var authCommand = new AuthCommand(TestUser.NONEXISTENT_EMAIL, TestUser.RAW_PASSWORD);

    try {
      authenticationService.authenticate(authCommand);
      Assertions.fail("Should throw IncorrectCredentialsException");
    } catch (IncorrectCredentials ex) {
      // expected
    }
  }

  @Test
  public void shouldThrowIncorrectCredentialsExceptionGivenWrongPassword() {
    User user = TestUser.newInstance();
    user.activate();

    Mockito.doReturn(Optional.of(user))
        .when(userRepository).findByEmail(TestUser.EMAIL);

    var authCommand = new AuthCommand(TestUser.EMAIL, TestUser.WRONG_RAW_PASSWORD);

    try {
      authenticationService.authenticate(authCommand);
      Assertions.fail("Should throw IncorrectCredentialsException");
    } catch (IncorrectCredentials ex) {
      // expected
    }
  }

  @Test
  public void shouldThrowInActiveExceptionIfAccountIsNotActivated() {
    User inactiveUser = TestUser.newInstance();
    Assertions.assertThat(inactiveUser.isActivated()).isFalse();

    Mockito.doReturn(Optional.of(inactiveUser))
        .when(userRepository).findByEmail(TestUser.EMAIL);

    var authCommand = new AuthCommand(TestUser.EMAIL, TestUser.RAW_PASSWORD);

    try {
      authenticationService.authenticate(authCommand);
      Assertions.fail("Should throw InactiveUserAccount");
    } catch (InactiveUserAccount ex) {
      // expected
    }
  }

  @Test
  public void shouldReturnTokenGivenCorrectCredentials() {
    User activeUser = TestUser.newInstance();
    activeUser.activate();

    Mockito.doReturn(Optional.of(activeUser))
        .when(userRepository).findByEmail(TestUser.EMAIL);

    var authCommand = new AuthCommand(TestUser.EMAIL, TestUser.RAW_PASSWORD);

    var token = authenticationService.authenticate(authCommand);
    Assertions.assertThat(token).isNotNull();
  }
}
