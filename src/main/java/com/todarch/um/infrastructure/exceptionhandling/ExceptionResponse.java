package com.todarch.um.infrastructure.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ExceptionResponse {
  private final String errorCode;
  private final String errorMessage;

  static final ExceptionResponse UNKNOWN_ERROR =
      new ExceptionResponse("unknown1", "Something unexpected happened. Try again later.");
}
