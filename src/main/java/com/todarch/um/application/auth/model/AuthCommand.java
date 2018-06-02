package com.todarch.um.application.auth.model;

import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.RawPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class AuthCommand {
  @NonNull
  private final Email email;
  @NonNull
  private final RawPassword rawPassword;
}
