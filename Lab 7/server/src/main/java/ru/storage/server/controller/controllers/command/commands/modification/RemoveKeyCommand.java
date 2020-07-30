package ru.storage.server.controller.controllers.command.commands.modification;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;
import ru.storage.common.ArgumentMediator;
import ru.storage.common.transfer.response.Response;
import ru.storage.common.transfer.response.Status;
import ru.storage.server.controller.services.parser.Parser;
import ru.storage.server.controller.services.parser.exceptions.ParserException;
import ru.storage.server.model.domain.entity.entities.user.User;
import ru.storage.server.model.domain.entity.entities.worker.Worker;
import ru.storage.server.model.domain.repository.Query;
import ru.storage.server.model.domain.repository.Repository;
import ru.storage.server.model.domain.repository.exceptions.RepositoryException;
import ru.storage.server.model.domain.repository.repositories.workerRepository.queries.GetEqualKeyWorkers;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class RemoveKeyCommand extends ModificationCommand {
  private static final Logger logger = LogManager.getLogger(RemoveKeyCommand.class);

  private final String removedSuccessfullyAnswer;

  public RemoveKeyCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      Repository<Worker> workerRepository,
      Parser parser,
      User user) {
    super(configuration, argumentMediator, arguments, locale, user, workerRepository, parser);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.RemoveCommand");

    removedSuccessfullyAnswer = resourceBundle.getString("answers.removedSuccessfully");
  }

  @Override
  public Response executeCommand() {
    Integer key;

    try {
      key = parser.parseInteger(arguments.get(argumentMediator.workerKey));
    } catch (ParserException e) {
      logger.warn(() -> "Got wrong remove key.", e);
      return new Response(Status.BAD_REQUEST, wrongKeyAnswer);
    }

    if (key == null) {
      logger.warn(() -> "Got null remove key.");
      return new Response(Status.BAD_REQUEST, wrongKeyAnswer);
    }

    Query<Worker> query = new GetEqualKeyWorkers(key);
    List<Worker> equalKeyWorkers;

    try {
      equalKeyWorkers = workerRepository.get(query);
    } catch (RepositoryException e) {
      logger.error("Cannot get workers which key is equal to {}.", (Supplier<?>) () -> key, e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    if (equalKeyWorkers.isEmpty()) {
      logger.info("Worker with specified key: {} was not found.", () -> key);
      return new Response(Status.NOT_FOUND, workerNotFoundAnswer);
    }

    for (Worker worker : equalKeyWorkers) {
      try {
        if (worker.getOwnerId() == user.getId()) {
          workerRepository.delete(worker);
        } else {
          return new Response(Status.FORBIDDEN, notOwnerAnswer);
        }
      } catch (RepositoryException e) {
        logger.error("Cannot remove worker which key is equal to {}.", (Supplier<?>) () -> key, e);
        return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
      }
    }

    logger.info(() -> "Worker was removed.");
    return new Response(Status.OK, removedSuccessfullyAnswer);
  }
}
