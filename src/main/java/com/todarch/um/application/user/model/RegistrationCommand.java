package com.todarch.um.application.user.model;

import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.RawPassword;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class RegistrationCommand {
  private Email email;
  private RawPassword rawPassword;

  public RegistrationCommand(@NonNull Email email,
                             @NonNull RawPassword rawPassword) {
    this.email = email;
    this.rawPassword = rawPassword;
  }
}
