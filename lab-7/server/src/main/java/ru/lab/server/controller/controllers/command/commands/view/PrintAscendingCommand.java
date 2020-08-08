package ru.lab.server.controller.controllers.command.commands.view;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.server.controller.services.parser.Parser;
import ru.lab.server.model.domain.entity.entities.worker.Worker;
import ru.lab.server.model.domain.repository.Query;
import ru.lab.server.model.domain.repository.exceptions.RepositoryException;
import ru.lab.server.model.domain.repository.repositories.workerRepository.WorkerRepository;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.transfer.response.Response;
import ru.lab.common.transfer.response.Status;
import ru.lab.server.model.domain.repository.repositories.workerRepository.queries.GetAllWorkers;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public final class PrintAscendingCommand extends ViewCommand {
  private static final Logger logger = LogManager.getLogger(PrintAscendingCommand.class);

  private final String printAscendingPrefix;
  private final String collectionIsEmptyAnswer;

  public PrintAscendingCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      WorkerRepository workerRepository,
      Parser parser) {
    super(configuration, argumentMediator, arguments, locale, workerRepository, parser);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.PrintAscendingCommand");

    printAscendingPrefix = resourceBundle.getString("prefixes.printAscending");
    collectionIsEmptyAnswer = resourceBundle.getString("answers.collectionIsEmpty");
  }

  @Override
  public Response executeCommand() {
    Query<Worker> query = new GetAllWorkers();
    List<Worker> allWorkers;

    try {
      allWorkers = workerRepository.get(query);
    } catch (RepositoryException e) {
      logger.error(() -> "Cannot get all workers to sort.", e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    if (allWorkers.isEmpty()) {
      logger.info(() -> "Workers to sort not found.");
      return new Response(Status.NO_CONTENT, collectionIsEmptyAnswer);
    }

    List<Worker> sortedWorkers = allWorkers.stream().sorted().collect(Collectors.toList());

    StringBuilder result = new StringBuilder(printAscendingPrefix);

    for (Worker worker : sortedWorkers) {
      result
          .append(System.lineSeparator())
          .append(System.lineSeparator())
          .append(workerToString(worker));
    }

    logger.info(() -> "All workers were sorted and converted.");
    return new Response(Status.OK, result.toString());
  }
}
