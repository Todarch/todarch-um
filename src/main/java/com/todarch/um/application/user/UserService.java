package com.todarch.um.application.user;

import com.todarch.um.application.user.model.RegistrationCommand;

public interface UserService {

  void register(RegistrationCommand command);

}
