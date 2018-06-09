package com.todarch.um.infrastructure.config;

import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.domain.kernel.EncryptedPassword;
import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.RawPassword;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class DbPopulator implements CommandLineRunner {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public static final Email TEST_EMAIL = Email.from("test@user.com");
  public static final RawPassword TEST_PASSWORD = RawPassword.from("12345678");

  @Override
  public void run(String... args) throws Exception {
    EncryptedPassword encryptedPassword = TEST_PASSWORD.encryptWith(passwordEncoder);
    User user = new User(TEST_EMAIL, encryptedPassword);
    userRepository.saveAndFlush(user);
    log.info("test user is created.");
  }
}
