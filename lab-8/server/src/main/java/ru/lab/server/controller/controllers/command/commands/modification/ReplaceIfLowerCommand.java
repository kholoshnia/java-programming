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

public final class ReplaceIfLowerCommand extends ModificationCommand {
  private static final Logger logger = LogManager.getLogger(ReplaceIfLowerCommand.class);

  private final String notLowerWorkerAnswer;
  private final String removedSuccessfullyAnswer;

  public ReplaceIfLowerCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      Repository<Worker> workerRepository,
      Parser parser,
      User user) {
    super(configuration, argumentMediator, arguments, locale, user, workerRepository, parser);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.ReplaceIfLowerCommand");

    notLowerWorkerAnswer = resourceBundle.getString("answers.notLowerWorker");
    removedSuccessfullyAnswer = resourceBundle.getString("answers.removedSuccessfully");
  }

  @Override
  public Response executeCommand() {
    Integer key;

    try {
      key = parser.parseInteger(arguments.get(argumentMediator.workerKey));
    } catch (ParserException e) {
      logger.warn(() -> "Got wrong replace if lower key.", e);
      return new Response(Status.BAD_REQUEST, wrongKeyAnswer);
    }

    if (key == null) {
      logger.warn(() -> "Got null replace if lower key.");
      return new Response(Status.BAD_REQUEST, wrongKeyAnswer);
    }

    Query<Worker> query = new GetEqualKeyWorkers(key);
    List<Worker> equalKeyWorkers;

    try {
      equalKeyWorkers = workerRepository.get(query);
    } catch (RepositoryException e) {
      logger.error(
          "Cannot get workers which key is equal to {} to replace if lower.",
          (Supplier<?>) () -> key,
          e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    if (equalKeyWorkers.isEmpty()) {
      logger.info("Worker with specified key: {} was not found.", () -> key);
      return new Response(Status.NOT_FOUND, workerNotFoundAnswer);
    }

    WorkerDTO workerDTO;

    try {
      workerDTO = createWorkerDTO();
    } catch (ParserException e) {
      logger.warn(() -> "Cannot create worker DTO.", e);
      return new Response(Status.BAD_REQUEST, wrongWorkerFormatAnswer);
    }

    if (workerDTO == null) {
      logger.warn(() -> "Got null worker.");
      return new Response(Status.BAD_REQUEST, wrongWorkerFormatAnswer);
    }

    Worker target;

    try {
      target = workerDTO.toEntity();
    } catch (ValidationException e) {
      logger.warn(() -> "Got wrong worker data.", e);
      return new Response(Status.BAD_REQUEST, wrongWorkerFormatAnswer);
    }

    for (Worker worker : equalKeyWorkers) {
      try {
        if (worker.getOwnerId() == user.getId()) {
          if (target.compareTo(worker) < 0) {
            setId(worker, target);
            setOwnerId(target);
            target.setKey(worker.getKey());
            workerRepository.update(target);
          } else {
            return new Response(Status.NOT_MODIFIED, notLowerWorkerAnswer);
          }
        } else {
          return new Response(Status.FORBIDDEN, notOwnerAnswer);
        }
      } catch (RepositoryException | ValidationException e) {
        logger.error("Cannot remove worker which key is equal to {}.", (Supplier<?>) () -> key, e);
        return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
      }
    }

    logger.info(() -> "Worker was removed.");
    return new Response(Status.OK, removedSuccessfullyAnswer);
  }
}
