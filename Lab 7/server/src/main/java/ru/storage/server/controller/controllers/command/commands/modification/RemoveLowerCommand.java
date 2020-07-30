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
import ru.storage.server.model.domain.dto.dtos.WorkerDTO;
import ru.storage.server.model.domain.entity.entities.user.User;
import ru.storage.server.model.domain.entity.entities.worker.Worker;
import ru.storage.server.model.domain.entity.exceptions.ValidationException;
import ru.storage.server.model.domain.repository.Query;
import ru.storage.server.model.domain.repository.Repository;
import ru.storage.server.model.domain.repository.exceptions.RepositoryException;
import ru.storage.server.model.domain.repository.repositories.workerRepository.queries.GetLowerWorkers;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class RemoveLowerCommand extends ModificationCommand {
  private static final Logger logger = LogManager.getLogger(RemoveLowerCommand.class);

  private final String lowerWorkersNotFoundAnswer;
  private final String removedSuccessfullyAnswer;

  public RemoveLowerCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      Repository<Worker> workerRepository,
      Parser parser,
      User user) {
    super(configuration, argumentMediator, arguments, locale, user, workerRepository, parser);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.RemoveLowerCommand");

    lowerWorkersNotFoundAnswer = resourceBundle.getString("answers.lowerWorkersNotFound");
    removedSuccessfullyAnswer = resourceBundle.getString("answers.removedSuccessfully");
  }

  @Override
  public Response executeCommand() {
    WorkerDTO workerDTO;

    try {
      workerDTO = createWorkerDTO();
    } catch (ParserException e) {
      logger.warn(() -> "Cannot create remove lower worker DTO.", e);
      return new Response(Status.BAD_REQUEST, wrongWorkerFormatAnswer);
    }

    if (workerDTO == null) {
      logger.warn(() -> "Got null remove lower worker.");
      return new Response(Status.BAD_REQUEST, wrongWorkerFormatAnswer);
    }

    Worker target;

    try {
      target = workerDTO.toEntity();
    } catch (ValidationException e) {
      logger.warn(() -> "Got wrong worker data.", e);
      return new Response(Status.BAD_REQUEST, wrongWorkerFormatAnswer);
    }

    Query<Worker> query = new GetLowerWorkers(target);
    List<Worker> lowerWorkers;

    try {
      lowerWorkers = workerRepository.get(query);
    } catch (RepositoryException e) {
      logger.error("Cannot get workers lower than worker: {}.", (Supplier<?>) () -> target, e);
      return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    if (lowerWorkers.isEmpty()) {
      logger.info("Workers lower than the specified one: {} was not found.", () -> target);
      return new Response(Status.NOT_FOUND, lowerWorkersNotFoundAnswer);
    }

    for (Worker worker : lowerWorkers) {
      try {
        if (worker.getOwnerId() == user.getId()) {
          workerRepository.delete(worker);
        }
      } catch (RepositoryException e) {
        logger.error("Cannot remove worker: {}.", (Supplier<?>) () -> worker, e);
        return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
      }
    }

    logger.info(() -> "Worker was removed.");
    return new Response(Status.OK, removedSuccessfullyAnswer);
  }
}
