package ru.lab.server.controller.controllers.command.commands.entry;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.transfer.response.Response;
import ru.lab.common.transfer.response.Status;
import ru.lab.server.controller.services.hash.HashGenerator;
import ru.lab.server.model.domain.entity.entities.user.User;
import ru.lab.server.model.domain.repository.Repository;

import java.security.Key;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LogoutCommand extends EntryCommand {
  private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

  private final String loggedOutAnswer;

  public LogoutCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      Repository<User> userRepository,
      HashGenerator hashGenerator,
      Key key) {
    super(configuration, argumentMediator, arguments, locale, userRepository, hashGenerator, key);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.LogoutCommand", locale);

    loggedOutAnswer = resourceBundle.getString("answers.loggedOut");
  }

  @Override
  public Response executeCommand() {
    logger.info(
        () -> "Returning unauthorized answer, user must be unauthorized on the client size.");
    return new Response(Status.OK, loggedOutAnswer, "");
  }
}
