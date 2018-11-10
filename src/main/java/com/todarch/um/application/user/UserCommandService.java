package com.todarch.um.application.user;

import com.todarch.um.application.exception.ApplicationException;
import com.todarch.um.application.user.model.RegistrationCommand;
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
public class UserCommandService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final ProducerChannels producerChannels;

  /**
   * Registers a new user.
   * Generates user registration event.
   *
   * @param command user registration command.
   * @throws ApplicationException when email is not unique.
   */
  public void register(@NonNull RegistrationCommand command) {
    requireUniqueEmail(command.getEmail());

    RawPassword userRawPassword = command.getRawPassword();
    EncryptedPassword encryptedPassword = userRawPassword.encryptWith(passwordEncoder);
    User user = new User(command.getEmail(), encryptedPassword);
    userRepository.save(user);

    onUserRegistered(user);

    log.info("Created user with {}", command.getEmail());
  }

  private void onUserRegistered(User user) {
    UserRegisteredEvent userRegisteredEvent =
        new UserRegisteredEvent(user.email(), toActivationUrl(user.activationCode()));
    producerChannels.email().send(MessageBuilder.withPayload(userRegisteredEvent).build());
  }

  private void requireUniqueEmail(Email email) {
    userRepository.findByEmail(email)
        .ifPresent(ignore -> {
          throw new EmailAddressAlreadyInUse();
        });
  }

  //TODO:selimssevgi: extract base value to config
  private String toActivationUrl(String activationKey) {
    return "https://todarch.com/non-secured/activate-account?code=" + activationKey;
  }

  /**
   * Activates user account if activation code matches.
   *
   * @param activationCode activation code sent to user after registration.
   * @throws InvalidActivationCode if code does not have any match.
   */
  public void activateAccount(String activationCode) {
    User user =
        userRepository.findByActivationCode(activationCode)
            .orElseThrow(InvalidActivationCode::new);
    user.activate();
    log.info("{} is successfully activated.");
  }
}
