package ru.lab.server.controller.controllers.command.factory.factories;

import com.google.inject.Inject;
import org.apache.commons.configuration2.Configuration;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.CommandMediator;
import ru.lab.server.controller.controllers.command.Command;
import ru.lab.server.controller.controllers.command.commands.history.HistoryCommand;
import ru.lab.server.controller.controllers.command.commands.history.ShowHistoryCommand;
import ru.lab.server.controller.controllers.command.factory.CommandFactory;
import ru.lab.server.controller.controllers.command.factory.exceptions.CommandFactoryException;
import ru.lab.server.model.domain.history.History;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class HistoryCommandFactory extends CommandFactory {
  private final Configuration configuration;
  private final ArgumentMediator argumentMediator;
  private final History history;

  private final Map<String, Class<? extends HistoryCommand>> historyCommandMap;

  @Inject
  public HistoryCommandFactory(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      CommandMediator commandMediator,
      History history) {
    this.configuration = configuration;
    this.argumentMediator = argumentMediator;
    this.history = history;
    historyCommandMap = initHistoryCommandMap(commandMediator);
  }

  private Map<String, Class<? extends HistoryCommand>> initHistoryCommandMap(
      CommandMediator commandMediator) {
    return new HashMap<String, Class<? extends HistoryCommand>>() {
      {
        put(commandMediator.history, ShowHistoryCommand.class);
      }
    };
  }

  @Override
  public Command createCommand(
      String command, Map<String, String> arguments, Locale locale, String login)
      throws CommandFactoryException {
    Class<? extends HistoryCommand> clazz = historyCommandMap.get(command);

    try {
      Constructor<? extends HistoryCommand> constructor =
          clazz.getConstructor(
              Configuration.class, ArgumentMediator.class, Map.class, Locale.class, History.class);

      return constructor.newInstance(configuration, argumentMediator, arguments, locale, history);
    } catch (NoSuchMethodException
        | InstantiationException
        | IllegalAccessException
        | InvocationTargetException e) {
      throw new CommandFactoryException(e);
    }
  }
}
