package ru.lab.server.controller.controllers.argument.validator.validators;

import com.google.inject.Inject;
import ru.lab.common.ArgumentMediator;
import ru.lab.server.controller.controllers.argument.validator.ArgumentValidator;

import java.util.ArrayList;

public final class RegisterValidator extends ArgumentValidator {
  @Inject
  public RegisterValidator(ArgumentMediator argumentMediator) {
    super(
        new ArrayList<String>() {
          {
            add(argumentMediator.userName);
            add(argumentMediator.userLogin);
            add(argumentMediator.userPassword);
          }
        });
  }
}
