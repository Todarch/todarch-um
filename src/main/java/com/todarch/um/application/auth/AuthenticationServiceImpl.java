package com.todarch.um.application.auth;

import com.todarch.security.api.JwtUtil;
import com.todarch.um.application.auth.model.AuthCommand;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.domain.shared.Jwt;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  @Override
  public Jwt authenticate(@NonNull AuthCommand authCommand) {
    User user =
        userRepository.findByEmail(authCommand.getEmail())
            .orElseThrow(IncorrectCredentials::new);

    if (!user.isActivated()) {
      throw new InactiveUserAccount();
    }

    try {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(
              authCommand.getEmail().value(),
              authCommand.getRawPassword().value());

      Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

      String token = jwtUtil.createToken(authentication, false, user.id());
      log.info("Created token for {}", authCommand.getEmail());
      return Jwt.from(token);
    } catch (BadCredentialsException badCreds) {
      throw new IncorrectCredentials();
    }
  }
}
