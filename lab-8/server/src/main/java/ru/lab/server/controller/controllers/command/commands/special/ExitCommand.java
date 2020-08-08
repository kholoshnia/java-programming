package ru.lab.server.controller.controllers.command.commands.special;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.CommandMediator;
import ru.lab.common.exitManager.ExitManager;
import ru.lab.common.transfer.response.Response;
import ru.lab.common.transfer.response.Status;
import ru.lab.server.controller.services.script.scriptExecutor.ScriptExecutor;
import ru.lab.server.model.domain.entity.entities.user.User;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ExitCommand extends SpecialCommand {
  private static final Logger logger = LogManager.getLogger(ExitCommand.class);

  private final String clientExitAnswer;

  public ExitCommand(
      Configuration configuration,
      CommandMediator commandMediator,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      User user,
      Locale locale,
      ExitManager exitManager,
      ScriptExecutor scriptExecutor) {
    super(
        configuration,
        commandMediator,
        argumentMediator,
        arguments,
        user,
        locale,
        exitManager,
        scriptExecutor);
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.ExitCommand", locale);

    clientExitAnswer = resourceBundle.getString("answers.clientExit");
  }

  @Override
  public Response executeCommand() {
    logger.info(() -> "Exit must happen on the client.");
    return new Response(Status.BAD_REQUEST, clientExitAnswer);
  }
}
