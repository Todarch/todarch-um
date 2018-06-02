package com.todarch.um.rest.account;

import com.todarch.um.Endpoints;
import com.todarch.um.domain.User;
import com.todarch.um.domain.shared.Jwt;
import com.todarch.um.helper.BaseIntTest;
import com.todarch.um.infrastructure.security.JwtTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerIntTest extends BaseIntTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldNotAccessAccountWithoutAuthToken() throws Exception {
    mockMvc
        .perform(
            get(Endpoints.ACCOUNT)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldReturnCurrentUserInformation() throws Exception {
    User user = dbHelper.createTestUser();
    Jwt jwt = dbHelper.createJwt(user);

    mockMvc
        .perform(
            get(Endpoints.ACCOUNT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(JwtTokenUtil.AUTH_HEADER, JwtTokenUtil.AUTH_PREFIX + jwt.token()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(user.email().value()));
  }
}
