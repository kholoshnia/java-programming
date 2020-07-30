package ru.storage.server.controller.controllers.command.commands.modification;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;
import ru.storage.common.ArgumentMediator;
import ru.storage.common.transfer.response.Response;
import ru.storage.common.transfer.response.Status;
import ru.storage.server.controller.services.parser.Parser;
import ru.storage.server.model.domain.entity.entities.user.User;
import ru.storage.server.model.domain.entity.entities.worker.Worker;
import ru.storage.server.model.domain.repository.Query;
import ru.storage.server.model.domain.repository.Repository;
import ru.storage.server.model.domain.repository.exceptions.RepositoryException;
import ru.storage.server.model.domain.repository.repositories.workerRepository.queries.GetAllWorkers;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class ClearCommand extends ModificationCommand {
  private static final Logger logger = LogManager.getLogger(ClearCommand.class);

  private final String clearedSuccessfullyAnswer;

  public ClearCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      Repository<Worker> workerRepository,
      Parser parser,
      User user) {
    super(configuration, argumentMediator, arguments, locale, user, workerRepository, parser);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.ClearCommand");

    clearedSuccessfullyAnswer = resourceBundle.getString("answers.clearedSuccessfully");
  }

  @Override
  public Response executeCommand() {
    Query<Worker> query = new GetAllWorkers();
    List<Worker> allWorkers;

    try {
      allWorkers = workerRepository.get(query);
    } catch (RepositoryException e) {
      logger.error(() -> "Cannot get all workers to clear.", e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    if (allWorkers.isEmpty()) {
      logger.info(() -> "Workers not found.");
      return new Response(Status.NO_CONTENT, collectionIsEmptyAnswer);
    }

    for (Worker worker : allWorkers) {
      try {
        if (worker.getOwnerId() == user.getId()) {
          workerRepository.delete(worker);
        }
      } catch (RepositoryException e) {
        logger.error("Cannot remove worker white clearing: {}.", (Supplier<?>) () -> worker, e);
        return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
      }
    }

    logger.info(() -> "User workers collection was cleared.");
    return new Response(Status.OK, clearedSuccessfullyAnswer);
  }
}
