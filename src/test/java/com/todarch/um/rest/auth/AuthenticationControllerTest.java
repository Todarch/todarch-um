package com.todarch.um.rest.auth;

import com.todarch.security.api.JwtUtil;
import com.todarch.um.Endpoints;
import com.todarch.um.domain.User;
import com.todarch.um.domain.shared.Jwt;
import com.todarch.um.helper.BaseIntTest;
import com.todarch.um.helper.TestUser;
import com.todarch.um.helper.TestUtil;
import com.todarch.um.rest.auth.model.AuthRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationControllerTest extends BaseIntTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void givenCorrectCredentialsShouldAuthenticate() throws Exception {
    User testUser = dbHelper.createTestUser();

    AuthRequest req = new AuthRequest();
    req.setEmail(testUser.email().value());
    req.setPassword(TestUser.RAW_PASSWORD.value());

    mockMvc
        .perform(
            post(Endpoints.AUTHENTICATION)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.toJsonBytes(req)))
        .andExpect(status().isOk())
        .andExpect(header().exists(JwtUtil.AUTH_HEADER))
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  public void userIsAuthenticatedIfHasValidToken() throws Exception {
    User user = dbHelper.createTestUser();
    Jwt jwt = dbHelper.createJwt(user);

    mockMvc
        .perform(
            get(Endpoints.AUTHENTICATE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(JwtUtil.AUTH_HEADER, JwtUtil.AUTH_PREFIX + jwt.token()))
        .andExpect(status().isNoContent());
  }

  @Test
  public void userIsNotAuthenticatedGivenNoToken() throws Exception {
    mockMvc
        .perform(
            get(Endpoints.AUTHENTICATE)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isForbidden());
  }

  @Test
  public void logoutReturns204() throws Exception {
    User user = dbHelper.createTestUser();
    Jwt jwt = dbHelper.createJwt(user);
    mockMvc
        .perform(
            post(Endpoints.LOGOUT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(JwtUtil.AUTH_HEADER, JwtUtil.AUTH_PREFIX + jwt.token()))
        .andExpect(status().isNoContent());
  }
}
