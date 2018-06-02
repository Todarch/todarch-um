package com.todarch.um.application.user;

import com.todarch.um.application.user.model.RegistrationCommand;
import com.todarch.um.application.user.model.UserDto;

public interface UserService {

  void register(RegistrationCommand command);

  UserDto getAccount();
}
