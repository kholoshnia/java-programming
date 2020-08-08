package ru.lab.server.controller.services.script.scriptExecutor.argumentFormer.argumentFormers;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.common.ArgumentMediator;
import ru.lab.server.controller.services.script.Script;
import ru.lab.server.controller.services.script.scriptExecutor.argumentFormer.exceptions.WrongArgumentsException;

import java.util.*;

public final class KeyFormer extends Former {
  private static final Logger logger = LogManager.getLogger(KeyFormer.class);

  private final ArgumentMediator argumentMediator;

  private String wrongArgumentsNumberException;

  @Inject
  public KeyFormer(ArgumentMediator argumentMediator) {
    super(argumentMediator);
    this.argumentMediator = argumentMediator;
  }

  @Override
  protected void changeLocale(Locale locale) {
    super.changeLocale(locale);
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.KeyFormer");

    wrongArgumentsNumberException = resourceBundle.getString("exceptions.wrongArgumentsNumber");
  }

  @Override
  public void check(List<String> arguments) throws WrongArgumentsException {
    if (arguments.size() != 1) {
      logger.warn(() -> "Got wrong arguments number.");
      throw new WrongArgumentsException(wrongArgumentsNumberException);
    }
  }

  @Override
  public Map<String, String> form(List<String> arguments, Script script) {
    Map<String, String> allArguments = new HashMap<>();
    allArguments.put(argumentMediator.workerKey, arguments.get(0));

    logger.info(() -> "All arguments were formed.");
    return allArguments;
  }
}
