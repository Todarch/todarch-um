package com.todarch.um.rest.model;

import lombok.Data;

@Data
public class RegistrationRequest {
  private String email;
  private String password;
}
