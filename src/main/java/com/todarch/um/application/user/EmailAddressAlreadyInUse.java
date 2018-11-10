package com.todarch.um.application.user;

import com.todarch.um.application.exception.ApplicationException;

public class EmailAddressAlreadyInUse extends ApplicationException {

  public EmailAddressAlreadyInUse() {
    super("user", "1", "Email address already in use.");
  }
}
