package com.todarch.um.rest.registration.model;

import lombok.Data;

@Data
public class RegistrationRequest {
  private String email;
  private String password;
}
