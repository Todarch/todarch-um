package com.todarch.um.application.user;

import com.todarch.um.application.user.model.RegistrationCommand;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.domain.kernel.EncryptedPassword;
import com.todarch.um.domain.shared.RawPassword;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public void register(@NonNull RegistrationCommand command) {
    RawPassword userRawPassword = command.getRawPassword();
    EncryptedPassword encryptedPassword = userRawPassword.encryptWith(passwordEncoder);
    User user = new User(command.getEmail(), encryptedPassword);
    userRepository.save(user);
    log.info("Created user with {}", command.getEmail());
  }
}
