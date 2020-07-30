package ru.storage.server.controller.controllers.argument.validator.validators;

import com.google.inject.Inject;
import ru.storage.common.ArgumentMediator;
import ru.storage.server.controller.controllers.argument.validator.ArgumentValidator;
import ru.storage.server.controller.controllers.argument.validator.exceptions.WrongValueException;
import ru.storage.server.controller.services.parser.Parser;
import ru.storage.server.controller.services.parser.exceptions.ParserException;

import java.util.ArrayList;
import java.util.Map;

public final class DateValidator extends ArgumentValidator {
  private final ArgumentMediator argumentMediator;
  private final Parser parser;

  @Inject
  public DateValidator(ArgumentMediator argumentMediator, Parser parser) {
    super(
        new ArrayList<String>() {
          {
            add(argumentMediator.workerStartDate);
          }
        });
    this.argumentMediator = argumentMediator;
    this.parser = parser;
  }

  @Override
  protected void checkValue(Map<String, String> arguments) throws WrongValueException {
    super.checkValue(arguments);

    String startDateString = arguments.get(argumentMediator.workerStartDate);

    try {
      parser.parseDate(startDateString);
    } catch (ParserException e) {
      throw new WrongValueException(e);
    }
  }
}
