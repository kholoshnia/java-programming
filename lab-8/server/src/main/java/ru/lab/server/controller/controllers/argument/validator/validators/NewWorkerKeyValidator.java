package ru.lab.server.controller.controllers.argument.validator.validators;

import com.google.inject.Inject;
import ru.lab.common.ArgumentMediator;
import ru.lab.server.controller.controllers.argument.validator.exceptions.WrongNumberException;
import ru.lab.server.controller.controllers.argument.validator.exceptions.WrongValueException;
import ru.lab.server.controller.services.parser.Parser;
import ru.lab.server.controller.services.parser.exceptions.ParserException;

import java.util.Map;

public final class NewWorkerKeyValidator extends WorkerValidator {
  private final ArgumentMediator argumentMediator;
  private final Parser parser;

  @Inject
  public NewWorkerKeyValidator(ArgumentMediator argumentMediator, Parser parser) {
    super(argumentMediator);
    requiredArguments.add(argumentMediator.workerKey);
    this.argumentMediator = argumentMediator;
    this.parser = parser;
  }

  @Override
  protected void checkNumber(Map<String, String> arguments) throws WrongNumberException {
    if (arguments.size() <= 1) {
      throw new WrongNumberException(WRONG_ARGUMENTS_NUMBER_EXCEPTION);
    }
  }

  @Override
  protected void checkValue(Map<String, String> arguments) throws WrongValueException {
    super.checkValue(arguments);

    String keyString = arguments.get(argumentMediator.workerKey);

    try {
      parser.parseInteger(keyString);
    } catch (ParserException e) {
      throw new WrongValueException(e);
    }
  }
}
