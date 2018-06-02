package com.todarch.um.rest;

import com.todarch.um.Endpoints;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.helper.BaseIntTest;
import com.todarch.um.helper.TestUser;
import com.todarch.um.helper.TestUtil;
import com.todarch.um.rest.model.RegistrationRequest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RegistrationControllerIntTest extends BaseIntTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldRegisterNewUser() throws Exception {
    RegistrationRequest req = new RegistrationRequest();
    req.setEmail(TestUser.EMAIL.value());
    req.setPassword(TestUser.RAW_PASSWORD.value());

    mockMvc
        .perform(MockMvcRequestBuilders.post(Endpoints.REGISTRATION)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(TestUtil.toJsonBytes(req)))
        .andExpect(MockMvcResultMatchers.status().isOk());

    User registeredUser = userRepository.findByEmail(TestUser.EMAIL).orElse(null);
    Assertions.assertThat(registeredUser).isNotNull();
  }
}
