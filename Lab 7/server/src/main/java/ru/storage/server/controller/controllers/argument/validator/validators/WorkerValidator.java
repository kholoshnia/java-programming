package ru.storage.server.controller.controllers.argument.validator.validators;

import com.google.inject.Inject;
import ru.storage.common.ArgumentMediator;
import ru.storage.server.controller.controllers.argument.validator.ArgumentValidator;
import ru.storage.server.controller.controllers.argument.validator.exceptions.WrongValueException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public abstract class WorkerValidator extends ArgumentValidator {
  @Inject
  public WorkerValidator(ArgumentMediator argumentMediator) {
    super(
        new ArrayList<String>() {
          {
            add(argumentMediator.worker);
            add(argumentMediator.workerName);
            add(argumentMediator.workerSalary);
            add(argumentMediator.workerStartDate);
            add(argumentMediator.workerEndDate);
            add(argumentMediator.workerStatus);

            add(argumentMediator.coordinates);
            add(argumentMediator.coordinatesX);
            add(argumentMediator.coordinatesY);

            add(argumentMediator.person);
            add(argumentMediator.personPassportId);
            add(argumentMediator.personEyeColor);
            add(argumentMediator.personHairColor);

            add(argumentMediator.location);
            add(argumentMediator.locationX);
            add(argumentMediator.locationY);
            add(argumentMediator.locationZ);
            add(argumentMediator.locationName);
          }
        });
  }

  @Override
  protected void checkValue(Map<String, String> arguments) throws WrongValueException {
    Set<String> argumentNames = arguments.keySet();

    for (String argumentName : argumentNames) {
      if (!requiredArguments.contains(argumentName)) {
        throw new WrongValueException(WRONG_ARGUMENTS_VALUE_EXCEPTION);
      }
    }
  }
}
