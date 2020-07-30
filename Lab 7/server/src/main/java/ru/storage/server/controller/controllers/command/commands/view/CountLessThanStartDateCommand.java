package ru.storage.server.controller.controllers.command.commands.view;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.storage.common.ArgumentMediator;
import ru.storage.common.transfer.response.Response;
import ru.storage.common.transfer.response.Status;
import ru.storage.server.controller.services.parser.Parser;
import ru.storage.server.controller.services.parser.exceptions.ParserException;
import ru.storage.server.model.domain.entity.entities.worker.Worker;
import ru.storage.server.model.domain.repository.Query;
import ru.storage.server.model.domain.repository.exceptions.RepositoryException;
import ru.storage.server.model.domain.repository.repositories.workerRepository.WorkerRepository;
import ru.storage.server.model.domain.repository.repositories.workerRepository.queries.GetLessStartDateWorkers;

import java.util.*;

public final class CountLessThanStartDateCommand extends ViewCommand {
  private static final Logger logger = LogManager.getLogger(CountLessThanStartDateCommand.class);

  private static final String PATTERN = "%s %d";

  private final String wrongStartDateAnswer;
  private final String lessStartDateWorkersNotFoundAnswer;
  private final String numberOfLessStartDateWorkersAnswer;

  public CountLessThanStartDateCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      WorkerRepository workerRepository,
      Parser parser) {
    super(configuration, argumentMediator, arguments, locale, workerRepository, parser);

    ResourceBundle resourceBundle =
        ResourceBundle.getBundle("localized.CountLessThanStartDateCommand");

    wrongStartDateAnswer = resourceBundle.getString("answers.wrongStartDate");
    lessStartDateWorkersNotFoundAnswer =
        resourceBundle.getString("answers.lessStartDateWorkersNotFound");
    numberOfLessStartDateWorkersAnswer =
        resourceBundle.getString("answers.numberOfLessStartDateWorkers");
  }

  @Override
  public Response executeCommand() {
    Date startDate;

    try {
      startDate = parser.parseDate(arguments.get(argumentMediator.workerStartDate));
    } catch (ParserException e) {
      logger.warn(() -> "Got wrong start date.", e);
      return new Response(Status.BAD_REQUEST, wrongStartDateAnswer);
    }

    if (startDate == null) {
      logger.warn(() -> "Got null start date.");
      return new Response(Status.BAD_REQUEST, wrongStartDateAnswer);
    }

    Query<Worker> query = new GetLessStartDateWorkers(startDate);
    List<Worker> lessStartDateWorkers;

    try {
      lessStartDateWorkers = workerRepository.get(query);
    } catch (RepositoryException e) {
      logger.error(() -> "Cannot get less start date workers.", e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    if (lessStartDateWorkers.isEmpty()) {
      logger.info(() -> "Less start date workers not found.");
      return new Response(Status.NO_CONTENT, lessStartDateWorkersNotFoundAnswer);
    }

    String result =
        String.format(PATTERN, numberOfLessStartDateWorkersAnswer, lessStartDateWorkers.size());

    logger.info(() -> "Less than start date workers was found.");
    return new Response(Status.OK, result);
  }
}
