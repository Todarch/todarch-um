package com.todarch.um.infrastructure.exceptionhandling;

import com.todarch.um.application.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Converts application exceptions to rest error response.
   */
  @ExceptionHandler({ApplicationException.class})
  public ResponseEntity<ExceptionResponse> handleAppEx(Exception ex, WebRequest request) {
    ApplicationException appEx = (ApplicationException) ex;
    var exceptionResponse = new ExceptionResponse(appEx.errorCode(), appEx.errorMessage());
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(exceptionResponse);
  }

  /**
   * Handles all other unknown errors.
   */
  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ExceptionResponse> handleAllOther(Exception ex, WebRequest request) {
    return ResponseEntity
        .status(HttpStatus.EXPECTATION_FAILED)
        .body(ExceptionResponse.UNKNOWN_ERROR);
  }
}
