package com.todarch.um;

import com.todarch.um.application.user.EmailAddressAlreadyInUse;
import com.todarch.um.application.user.UserCommandService;
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

  @MockBean
  private UserCommandService userCommandService;

  /**
   * Set up mocks and configuration for generated tests.
   */
  @Before
  public void setup() {
    Mockito.doNothing()
        .when(userCommandService).register(any());

    Mockito.doThrow(new EmailAddressAlreadyInUse())
        .when(userCommandService)
        .register(argThat(cmd -> TestUser.REGISTERED_EMAIL.equals(cmd.getEmail())));

    RestAssuredMockMvc.mockMvc(mockMvc);
  }
}
