package com.todarch.um.rest.auth.model;

import lombok.Data;

@Data
public class AuthRequest {
  private String email;
  private String password;
}
