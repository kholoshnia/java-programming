package ru.lab.client.controller.argumentFormer.argumentFormers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.client.controller.argumentFormer.ArgumentValidator;
import ru.lab.client.controller.argumentFormer.exceptions.CancelException;
import ru.lab.client.controller.localeManager.LocaleListener;
import ru.lab.client.view.console.Console;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.CommandMediator;

import java.util.*;

public abstract class WorkerFormer extends Former implements LocaleListener {
  private static final Logger logger = LogManager.getLogger(WorkerFormer.class);

  protected final ArgumentMediator argumentMediator;

  private String workerOffer;
  private String coordinatesOffer;
  private String personOffer;
  private String locationOffer;

  private Map<String, String> workerOffers;
  private Map<String, String> coordinatesOffers;
  private Map<String, String> personOffers;
  private Map<String, String> locationOffers;

  public WorkerFormer(
      CommandMediator commandMediator,
      Console console,
      Map<String, ArgumentValidator> validatorMap,
      ArgumentMediator argumentMediator) {
    super(commandMediator, console, validatorMap);
    this.argumentMediator = argumentMediator;
  }

  private Map<String, String> initWorkerOffers(ResourceBundle resourceBundle) {
    return new LinkedHashMap<String, String>() {
      {
        put(
            argumentMediator.workerName,
            String.format("%s: ", resourceBundle.getString("offers.worker.name")));
        put(
            argumentMediator.workerSalary,
            String.format("%s: ", resourceBundle.getString("offers.worker.salary")));
        put(
            argumentMediator.workerStartDate,
            String.format("%s: ", resourceBundle.getString("offers.worker.startDate")));
        put(
            argumentMediator.workerEndDate,
            String.format("%s: ", resourceBundle.getString("offers.worker.endDate")));
        put(
            argumentMediator.workerStatus,
            String.format("%s: ", resourceBundle.getString("offers.worker.status")));
      }
    };
  }

  private Map<String, String> initCoordinatesOffers(ResourceBundle resourceBundle) {
    return new LinkedHashMap<String, String>() {
      {
        put(
            argumentMediator.coordinatesX,
            String.format("%s: ", resourceBundle.getString("offers.coordinates.x")));
        put(
            argumentMediator.coordinatesY,
            String.format("%s: ", resourceBundle.getString("offers.coordinates.y")));
      }
    };
  }

  private Map<String, String> initPersonOffers(ResourceBundle resourceBundle) {
    return new LinkedHashMap<String, String>() {
      {
        put(
            argumentMediator.personPassportId,
            String.format("%s: ", resourceBundle.getString("offers.person.passportId")));
        put(
            argumentMediator.personEyeColor,
            String.format("%s: ", resourceBundle.getString("offers.person.eyeColor")));
        put(
            argumentMediator.personHairColor,
            String.format("%s: ", resourceBundle.getString("offers.person.hairColor")));
      }
    };
  }

  private Map<String, String> initLocationOffers(ResourceBundle resourceBundle) {
    return new LinkedHashMap<String, String>() {
      {
        put(
            argumentMediator.locationX,
            String.format("%s: ", resourceBundle.getString("offers.location.x")));
        put(
            argumentMediator.locationY,
            String.format("%s: ", resourceBundle.getString("offers.location.y")));
        put(
            argumentMediator.locationZ,
            String.format("%s: ", resourceBundle.getString("offers.location.z")));
        put(
            argumentMediator.locationName,
            String.format("%s: ", resourceBundle.getString("offers.location.name")));
      }
    };
  }

  @Override
  public void changeLocale(Locale locale) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.WorkerFormer");

    workerOffer = resourceBundle.getString("offers.worker");
    coordinatesOffer = resourceBundle.getString("offers.coordinates");
    personOffer = resourceBundle.getString("offers.person");
    locationOffer = resourceBundle.getString("offers.location");

    workerOffers = initWorkerOffers(resourceBundle);
    coordinatesOffers = initCoordinatesOffers(resourceBundle);
    personOffers = initPersonOffers(resourceBundle);
    locationOffers = initLocationOffers(resourceBundle);
  }

  protected final Map<String, String> formWorker() throws CancelException {
    console.writeLine(workerOffer);

    Map<String, String> allArguments = new HashMap<>();
    allArguments.put(argumentMediator.worker, argumentMediator.include);

    allArguments.putAll(readArguments(workerOffers));
    logger.info(() -> "Worker arguments were formed.");

    allArguments.putAll(formCoordinates());
    allArguments.putAll(formPerson());

    logger.info(() -> "All arguments were formed.");
    return allArguments;
  }

  protected final Map<String, String> formCoordinates() throws CancelException {
    Map<String, String> coordinatesArguments = new HashMap<>();

    console.writeLine(coordinatesOffer);

    coordinatesArguments.put(argumentMediator.coordinates, argumentMediator.include);
    coordinatesArguments.putAll(readArguments(coordinatesOffers));
    logger.info(() -> "Coordinates arguments were formed.");

    return coordinatesArguments;
  }

  protected final Map<String, String> formPerson() throws CancelException {
    Map<String, String> personArguments = new HashMap<>();

    if (readArgumentQuestion(personOffer)) {
      personArguments.put(argumentMediator.person, argumentMediator.include);
      personArguments.putAll(readArguments(personOffers));
      logger.info(() -> "Person arguments were formed.");

      personArguments.putAll(formLocation());
    } else {
      personArguments.put(argumentMediator.person, null);
      logger.info(() -> "Person arguments were not formed.");
    }

    return personArguments;
  }

  protected final Map<String, String> formLocation() throws CancelException {
    Map<String, String> locationArguments = new HashMap<>();

    console.writeLine(locationOffer);

    locationArguments.put(argumentMediator.location, argumentMediator.include);
    locationArguments.putAll(readArguments(locationOffers));
    logger.info(() -> "Person arguments were formed.");

    return locationArguments;
  }
}
