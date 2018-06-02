package com.todarch.um.application.auth;

import com.todarch.um.application.auth.model.AuthCommand;
import com.todarch.um.domain.shared.Jwt;

public interface AuthenticationService {
  Jwt authenticate(AuthCommand authCommand);
}
