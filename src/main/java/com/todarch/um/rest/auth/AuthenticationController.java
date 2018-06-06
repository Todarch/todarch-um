package com.todarch.um.rest.auth;

import com.todarch.um.Endpoints;
import com.todarch.um.application.auth.AuthenticationService;
import com.todarch.um.application.auth.model.AuthCommand;
import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.Jwt;
import com.todarch.um.domain.shared.RawPassword;
import com.todarch.um.infrastructure.security.JwtTokenUtil;
import com.todarch.um.rest.auth.model.AuthRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  /**
   * Authenticates users.
   *
   * @param request authentication request
   * @param response response object
   */
  @PostMapping(Endpoints.AUTHENTICATION)
  public void authenticate(@RequestBody AuthRequest request, HttpServletResponse response) {
    Email email = Email.from(request.getEmail());
    RawPassword password = RawPassword.from(request.getPassword());

    AuthCommand authCommand = new AuthCommand(email, password);
    Jwt jwt = authenticationService.authenticate(authCommand);
    response.addHeader(JwtTokenUtil.AUTH_HEADER, JwtTokenUtil.AUTH_PREFIX + jwt.token());
  }

  /**
   * Returns 200 if user can read this endpoint.
   */
  @GetMapping(Endpoints.AUTHENTICATE)
  public void isAuthenticated() {
    // do nothing, enough to reach this point
  }
}

