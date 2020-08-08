package ru.lab.server.controller.services.script.scriptExecutor.argumentFormer.argumentFormers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.common.ArgumentMediator;
import ru.lab.server.controller.services.script.Script;
import ru.lab.server.controller.services.script.scriptExecutor.argumentFormer.exceptions.WrongArgumentsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class WorkerFormer extends Former {
  private static final Logger logger = LogManager.getLogger(WorkerFormer.class);

  private final List<String> arguments;

  public WorkerFormer(ArgumentMediator argumentMediator) {
    super(argumentMediator);
    arguments = initArguments();
  }

  private List<String> initArguments() {
    return new ArrayList<String>() {
      {
        add(argumentMediator.worker);
        add(argumentMediator.workerName);
        add(argumentMediator.workerSalary);
        add(argumentMediator.workerStartDate);
        add(argumentMediator.workerEndDate);
        add(argumentMediator.workerStatus);

        add(argumentMediator.coordinates);
        add(argumentMediator.coordinatesX);
        add(argumentMediator.coordinatesY);

        add(argumentMediator.person);
        add(argumentMediator.personPassportId);
        add(argumentMediator.personEyeColor);
        add(argumentMediator.personHairColor);

        add(argumentMediator.location);
        add(argumentMediator.locationX);
        add(argumentMediator.locationY);
        add(argumentMediator.locationZ);
        add(argumentMediator.locationName);
      }
    };
  }

  protected final Map<String, String> formWorker(Script script) throws WrongArgumentsException {
    Map<String, String> allArguments = readArguments(arguments, script);

    logger.info(() -> "All arguments were formed.");
    return allArguments;
  }
}
