package ru.lab.server.controller.controllers.command.commands.modification;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.transfer.response.Response;
import ru.lab.common.transfer.response.Status;
import ru.lab.server.controller.services.parser.Parser;
import ru.lab.server.controller.services.parser.exceptions.ParserException;
import ru.lab.server.model.domain.dto.dtos.WorkerDTO;
import ru.lab.server.model.domain.entity.entities.user.User;
import ru.lab.server.model.domain.entity.entities.worker.Worker;
import ru.lab.server.model.domain.entity.exceptions.ValidationException;
import ru.lab.server.model.domain.repository.Query;
import ru.lab.server.model.domain.repository.Repository;
import ru.lab.server.model.domain.repository.exceptions.RepositoryException;
import ru.lab.server.model.domain.repository.repositories.workerRepository.queries.GetEqualKeyWorkers;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class InsertCommand extends ModificationCommand {
  private static final Logger logger = LogManager.getLogger(InsertCommand.class);

  private final String addedSuccessfullyAnswer;

  public InsertCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      Repository<Worker> workerRepository,
      Parser parser,
      User user) {
    super(configuration, argumentMediator, arguments, locale, user, workerRepository, parser);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.InsertCommand");

    addedSuccessfullyAnswer = resourceBundle.getString("answers.addedSuccessfully");
  }

  @Override
  public Response executeCommand() {
    Integer key;

    try {
      key = parser.parseInteger(arguments.get(argumentMediator.workerKey));
    } catch (ParserException e) {
      logger.warn(() -> "Got wrong insert key.", e);
      return new Response(Status.BAD_REQUEST, wrongKeyAnswer);
    }

    if (key == null) {
      logger.warn(() -> "Got null insert key.");
      return new Response(Status.BAD_REQUEST, wrongKeyAnswer);
    }

    Query<Worker> query = new GetEqualKeyWorkers(key);
    List<Worker> equalKeyWorkers;

    try {
      equalKeyWorkers = workerRepository.get(query);
    } catch (RepositoryException e) {
      logger.error(
          "Cannot get workers with key equal to {} to insert.", (Supplier<?>) () -> key, e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    if (!equalKeyWorkers.isEmpty()) {
      logger.info("Worker with specified key: {} already exists.", () -> key);
      return new Response(Status.CONFLICT, workerAlreadyExistsAnswer);
    }

    WorkerDTO workerDTO;

    try {
      workerDTO = createWorkerDTO();
    } catch (ParserException e) {
      logger.warn(() -> "Cannot create insert worker DTO.", e);
      return new Response(Status.BAD_REQUEST, wrongWorkerFormatAnswer);
    }

    if (workerDTO == null) {
      logger.warn(() -> "Got null insert worker.");
      return new Response(Status.BAD_REQUEST, wrongWorkerFormatAnswer);
    }

    try {
      Worker worker = workerDTO.toEntity();
      setOwnerId(worker);
      worker.setKey(key);
      workerRepository.insert(worker);
    } catch (ValidationException e) {
      logger.error(() -> "Cannot create worker from DTO.", e);
      return new Response(Status.BAD_REQUEST, wrongWorkerDataAnswer);
    } catch (RepositoryException e) {
      logger.error(() -> "Cannot add worker.", e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    logger.info(() -> "Worker was added.");
    return new Response(Status.CREATED, addedSuccessfullyAnswer);
  }
}
