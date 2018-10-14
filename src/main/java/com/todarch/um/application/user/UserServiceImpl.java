package com.todarch.um.application.user;

import com.todarch.security.api.SecurityUtil;
import com.todarch.security.api.UserContext;
import com.todarch.um.application.user.model.RegistrationCommand;
import com.todarch.um.application.user.model.NewUserRegistered;
import com.todarch.um.application.user.model.UserDto;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.domain.kernel.EncryptedPassword;
import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.RawPassword;
import com.todarch.um.infrastructure.event.EventPublisher;
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

  private final EventPublisher eventPublisher;

  @Override
  public void register(@NonNull RegistrationCommand command) {
    RawPassword userRawPassword = command.getRawPassword();
    EncryptedPassword encryptedPassword = userRawPassword.encryptWith(passwordEncoder);
    User user = new User(command.getEmail(), encryptedPassword);
    userRepository.save(user);
    log.info("Created user with {}", command.getEmail());

    NewUserRegistered newUserRegistered =
        new NewUserRegistered(command.getEmail(), "RandomActivationEmail");
    eventPublisher.publish(newUserRegistered);
  }

  @Override
  public UserDto getAccount() {
    UserContext userContext = SecurityUtil.tryToGetUserContext();
    String email = userContext.getEmail();
    User user = userRepository.findByEmail(Email.from(email))
        .orElseThrow(() -> new RuntimeException("User not found: " + email));
    UserDto userDto = new UserDto();
    userDto.setEmail(user.email().value());
    return userDto;
  }
}
