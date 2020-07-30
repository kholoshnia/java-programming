package ru.storage.server.controller.controllers.command.commands.special;

import org.apache.commons.configuration2.Configuration;
import ru.storage.common.ArgumentMediator;
import ru.storage.common.CommandMediator;
import ru.storage.common.exitManager.ExitManager;
import ru.storage.common.transfer.response.Response;
import ru.storage.common.transfer.response.Status;
import ru.storage.server.controller.services.script.scriptExecutor.ScriptExecutor;
import ru.storage.server.model.domain.entity.entities.user.User;

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
