package com.todarch.um.rest.registration;

import com.todarch.um.Endpoints;
import com.todarch.um.application.user.UserCommandService;
import com.todarch.um.application.user.model.RegistrationCommand;
import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.RawPassword;
import com.todarch.um.rest.registration.model.RegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class RegistrationController {

  private final UserCommandService userService;

  /**
   * Registers a new user.
   *
   * @param request new user information
   */
  @PostMapping(Endpoints.REGISTRATION)
  public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) {
    Email userEmail = Email.from(request.getEmail());
    RawPassword userPassword = RawPassword.from(request.getPassword());
    RegistrationCommand registrationCommand =
        new RegistrationCommand(userEmail, userPassword);

    userService.register(registrationCommand);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * Activates user account.
   */
  @PostMapping(Endpoints.ACTIVATION)
  public ResponseEntity<Void> activateAccount(@RequestParam(value = "code") String activationCode) {
    userService.activateAccount(activationCode);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
