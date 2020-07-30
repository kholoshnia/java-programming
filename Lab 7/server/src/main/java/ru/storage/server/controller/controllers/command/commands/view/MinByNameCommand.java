package ru.storage.server.controller.controllers.command.commands.view;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.storage.common.ArgumentMediator;
import ru.storage.common.transfer.response.Response;
import ru.storage.common.transfer.response.Status;
import ru.storage.server.controller.services.parser.Parser;
import ru.storage.server.model.domain.entity.entities.worker.Worker;
import ru.storage.server.model.domain.repository.Query;
import ru.storage.server.model.domain.repository.exceptions.RepositoryException;
import ru.storage.server.model.domain.repository.repositories.workerRepository.WorkerRepository;
import ru.storage.server.model.domain.repository.repositories.workerRepository.queries.GetMinNameWorkers;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class MinByNameCommand extends ViewCommand {
  private static final Logger logger = LogManager.getLogger(MinByNameCommand.class);

  private final String minByNamePrefix;
  private final String collectionIsEmptyAnswer;

  public MinByNameCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      WorkerRepository workerRepository,
      Parser parser) {
    super(configuration, argumentMediator, arguments, locale, workerRepository, parser);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.MinByNameCommand");

    minByNamePrefix = resourceBundle.getString("prefixes.minByName");
    collectionIsEmptyAnswer = resourceBundle.getString("answers.collectionIsEmpty");
  }

  @Override
  public Response executeCommand() {
    Query<Worker> query = new GetMinNameWorkers();
    List<Worker> minNameWorkers;

    try {
      minNameWorkers = workerRepository.get(query);
    } catch (RepositoryException e) {
      logger.error(() -> "Cannot get min name workers.", e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    if (minNameWorkers.isEmpty()) {
      logger.info(() -> "Mint name workers not found.");
      return new Response(Status.NO_CONTENT, collectionIsEmptyAnswer);
    }

    StringBuilder result = new StringBuilder(minByNamePrefix);

    for (Worker worker : minNameWorkers) {
      result
          .append(System.lineSeparator())
          .append(System.lineSeparator())
          .append(workerToString(worker));
    }

    logger.info(() -> "Min by name worker was found.");
    return new Response(Status.OK, result.toString());
  }
}
