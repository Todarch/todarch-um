package com.todarch.um.application.user.model;

import com.todarch.um.domain.shared.Email;
import com.todarch.um.infrastructure.event.BaseEvent;
import lombok.Data;

import java.util.UUID;

@Data
public class NewUserRegistered implements BaseEvent {
  private String eventId;
  private String email;
  private String activationUrl;

  /**
   * Construct new user registered event.
   */
  public NewUserRegistered(Email registeredEmail, String activationUrl) {
    this.eventId = UUID.randomUUID().toString();
    this.email = registeredEmail.value();
    this.activationUrl = activationUrl;
  }

  @Override
  public String getTopic() {
    return "dercg7n2-email";
  }
}
