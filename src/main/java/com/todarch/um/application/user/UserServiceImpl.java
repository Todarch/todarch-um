package com.todarch.um.application.user;

import com.todarch.security.api.SecurityUtil;
import com.todarch.security.api.UserContext;
import com.todarch.um.application.user.model.RegistrationCommand;
import com.todarch.um.application.user.model.UserDto;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.domain.kernel.EncryptedPassword;
import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.RawPassword;
import com.todarch.um.infrastructure.channel.ProducerChannels;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final ProducerChannels producerChannels;

  @Override
  public void register(@NonNull RegistrationCommand command) {
    RawPassword userRawPassword = command.getRawPassword();
    EncryptedPassword encryptedPassword = userRawPassword.encryptWith(passwordEncoder);
    User user = new User(command.getEmail(), encryptedPassword);
    userRepository.save(user);
    UserRegisteredEvent userRegisteredEvent =
        new UserRegisteredEvent(user.email(), toActivationUrl(UUID.randomUUID().toString()));
    producerChannels.email().send(MessageBuilder.withPayload(userRegisteredEvent).build());
    log.info("Created user with {}", command.getEmail());
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

  //TODO:selimssevgi: extract base value to config
  private String toActivationUrl(String activationKey) {
    return "https://todarch.com/non-secured/verify-email?code=" + activationKey;
  }
}
