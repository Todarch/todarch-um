package com.todarch.um.application.user;

import com.todarch.um.application.exception.ApplicationException;

public class InvalidActivationCode extends ApplicationException {

  public InvalidActivationCode() {
    super("user", "2", "Invalid activation code.");
  }
}

