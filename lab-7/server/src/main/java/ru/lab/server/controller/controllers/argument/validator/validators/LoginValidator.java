package ru.lab.server.controller.controllers.argument.validator.validators;

import com.google.inject.Inject;
import ru.lab.server.controller.controllers.argument.validator.ArgumentValidator;
import ru.lab.common.ArgumentMediator;

import java.util.ArrayList;

public final class LoginValidator extends ArgumentValidator {
  @Inject
  public LoginValidator(ArgumentMediator argumentMediator) {
    super(
        new ArrayList<String>() {
          {
            add(argumentMediator.userLogin);
            add(argumentMediator.userPassword);
          }
        });
  }
}
