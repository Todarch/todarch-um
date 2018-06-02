package com.todarch.um.application.auth;

import com.todarch.um.application.auth.model.AuthCommand;
import com.todarch.um.domain.shared.Jwt;
import com.todarch.um.infrastructure.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  public Jwt authenticate(@NonNull AuthCommand authCommand) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            authCommand.getEmail().value(),
            authCommand.getRawPassword().value());

    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
    Jwt token = jwtTokenUtil.createToken(authentication, false);
    log.info("Created token for {}", authCommand.getEmail());
    return token;
  }
}
