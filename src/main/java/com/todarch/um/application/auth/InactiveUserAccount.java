package com.todarch.um.application.auth;

import com.todarch.um.application.exception.ApplicationException;

public class InactiveUserAccount extends ApplicationException {
  public InactiveUserAccount() {
    super("user", "4", "Account is not active.");
  }
}
