package com.todarch.um.helper;

import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.domain.kernel.EncryptedPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class DbHelper {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Creates default test user using TestUser data.
   *
   * @return created test user object in detached state.
   */
  public User createTestUser() {
    EncryptedPassword encryptedPassword = TestUser.RAW_PASSWORD.encryptWith(passwordEncoder);
    User user = new User(TestUser.EMAIL, encryptedPassword);
    userRepository.saveAndFlush(user);
    return userRepository.findByEmail(TestUser.EMAIL).orElseThrow(onNotFound());
  }

  private Supplier<AssertionError> onNotFound() {
    return () -> new AssertionError("User should have been created");
  }

  public void clearAll() {
    userRepository.deleteAll();
  }
}

