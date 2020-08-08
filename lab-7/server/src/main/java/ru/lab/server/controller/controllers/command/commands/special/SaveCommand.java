package ru.lab.server.controller.controllers.command.commands.special;

import org.apache.commons.configuration2.Configuration;
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

public final class SaveCommand extends SpecialCommand {
  private final String updatingDataAnswer;

  public SaveCommand(
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

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.SaveCommand");

    updatingDataAnswer = resourceBundle.getString("answers.updatingData");
  }

  @Override
  public Response executeCommand() {
    return new Response(Status.OK, updatingDataAnswer);
  }
}
