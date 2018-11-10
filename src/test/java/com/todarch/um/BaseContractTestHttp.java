package com.todarch.um;

import com.todarch.security.api.JwtUtil;
import com.todarch.um.application.auth.AuthenticationService;
import com.todarch.um.application.auth.InactiveUserAccount;
import com.todarch.um.application.auth.IncorrectCredentials;
import com.todarch.um.application.user.EmailAddressAlreadyInUse;
import com.todarch.um.application.user.InvalidActivationCode;
import com.todarch.um.application.user.UserCommandService;
import com.todarch.um.domain.shared.Jwt;
import com.todarch.um.helper.DbHelper;
import com.todarch.um.helper.TestUser;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

/**
 * Base test configuration class for http based contract tests.
 *
 * @author selimssevgi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmApplication.class)
@AutoConfigureMockMvc
public abstract class BaseContractTestHttp {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DbHelper dbHelper;

  @MockBean
  private UserCommandService userCommandService;

  @MockBean
  private AuthenticationService authenticationService;

  /**
   * Set up mocks and configuration for generated tests.
   */
  @Before
  public void setup() {
    setupUserCommandService();

    setupAuthService();

    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  private void setupUserCommandService() {
    Mockito.doNothing()
        .when(userCommandService).register(any());

    Mockito.doThrow(new EmailAddressAlreadyInUse())
        .when(userCommandService)
        .register(argThat(cmd -> TestUser.REGISTERED_EMAIL.equals(cmd.getEmail())));

    Mockito.doNothing()
        .when(userCommandService).activateAccount(any());

    Mockito.doThrow(new InvalidActivationCode())
        .when(userCommandService).activateAccount("invalid-activation-code");
  }

  private void setupAuthService() {
    Mockito.doReturn(Jwt.from("validjwt"))
        .when(authenticationService)
        .authenticate(argThat(cmd -> TestUser.EMAIL.equals(cmd.getEmail())
            && TestUser.RAW_PASSWORD.equals(cmd.getRawPassword())));

    Mockito.doThrow(new InactiveUserAccount())
        .when(authenticationService)
        .authenticate(argThat(cmd -> TestUser.INACTIVE_EMAIL.equals(cmd.getEmail())));

    Mockito.doThrow(new IncorrectCredentials())
        .when(authenticationService)
        .authenticate(argThat(cmd -> TestUser.NONEXISTENT_EMAIL.equals(cmd.getEmail())));
  }

  public String authToken() {
    return JwtUtil.AUTH_PREFIX + dbHelper.createJwt(TestUser.newInstance()).token();
  }
}
