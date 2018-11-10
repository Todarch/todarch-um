package com.todarch.um.application.user;

import com.todarch.security.api.SecurityUtil;
import com.todarch.security.api.UserContext;
import com.todarch.um.application.user.model.UserDto;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.domain.shared.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * User querying service.
 *
 * @author selimssevgi
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserQueryService {

  private final UserRepository userRepository;

  /**
   * Returns details of a user account.
   */
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
