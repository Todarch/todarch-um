package com.todarch.um.rest.auth;

import com.todarch.security.api.JwtUtil;
import com.todarch.um.Endpoints;
import com.todarch.um.application.auth.AuthenticationService;
import com.todarch.um.application.auth.model.AuthCommand;
import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.Jwt;
import com.todarch.um.domain.shared.RawPassword;
import com.todarch.um.rest.auth.model.AuthRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  /**
   * Authenticates users.
   *  @param request authentication request
   */
  @PostMapping(Endpoints.AUTHENTICATION)
  public ResponseEntity<Object> authenticate(@RequestBody AuthRequest request) {
    Email email = Email.from(request.getEmail());
    RawPassword password = RawPassword.from(request.getPassword());

    AuthCommand authCommand = new AuthCommand(email, password);
    Jwt jwt = authenticationService.authenticate(authCommand);

    HttpHeaders headers = new HttpHeaders();
    headers.add(JwtUtil.AUTH_HEADER, JwtUtil.AUTH_PREFIX + jwt.token());
    return ResponseEntity.noContent().headers(headers).build();
  }

  /**
   * Returns 204 if user can read this endpoint.
   */
  @GetMapping(Endpoints.AUTHENTICATE)
  public ResponseEntity<Object> isAuthenticated() {
    return ResponseEntity.noContent().build();
  }

  /**
   * Revokes current users token.
   */
  @PostMapping(Endpoints.LOGOUT)
  public ResponseEntity<Object> logout() {
    log.info("Logging out user...");
    //TODO:selimssevgi: should revoke token
    return ResponseEntity.noContent().build();
  }
}

