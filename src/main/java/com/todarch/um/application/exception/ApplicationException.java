package com.todarch.um.application.exception;

/**
 * Defines the base structure of application exceptions.
 *
 * @author selimssevgi
 */
public abstract class ApplicationException extends RuntimeException {
  private final String module;
  private final String code;
  private final String error;

  protected ApplicationException(String module, String code, String error) {
    super(module.toUpperCase() + ":" + code + ":" + error);
    this.module = module.toUpperCase();
    this.code = code;
    this.error = error;
  }

  public String module() {
    return this.module;
  }

  public String code() {
    return this.code;
  }

  public String errorCode() {
    return module() + code();
  }

  public String errorMessage() {
    return this.error;
  }

}
