package com.todarch.um.application.auth;

import com.todarch.um.application.exception.ApplicationException;

public class IncorrectCredentials extends ApplicationException {
  public IncorrectCredentials() {
    super("user", "3", "Incorrect credentials.");
  }
}
