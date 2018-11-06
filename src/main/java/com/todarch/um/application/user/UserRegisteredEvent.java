package com.todarch.um.application.user;

import com.todarch.um.domain.shared.Email;
import lombok.Data;

import java.util.Map;

@Data
public class UserRegisteredEvent {
  private final String to;
  private final Map<String, String> parameters;
  private final String emailType;

  UserRegisteredEvent(Email to, String activationUrl) {
    this.to = to.value();
    this.parameters = Map.of("activation_url", activationUrl);
    this.emailType = "ACTIVATION_EMAIL";
  }
}
