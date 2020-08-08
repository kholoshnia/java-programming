package ru.lab.server.controller.controllers.command.commands.view;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.common.ArgumentMediator;
import ru.lab.server.controller.controllers.command.Command;
import ru.lab.server.controller.services.format.date.DateFormat;
import ru.lab.server.controller.services.format.eyeColor.EyeColorFormat;
import ru.lab.server.controller.services.format.hairColor.HairColorFormat;
import ru.lab.server.controller.services.format.number.NumberFormat;
import ru.lab.server.controller.services.format.status.StatusFormat;
import ru.lab.server.controller.services.parser.Parser;
import ru.lab.server.model.domain.entity.entities.worker.Coordinates;
import ru.lab.server.model.domain.entity.entities.worker.Worker;
import ru.lab.server.model.domain.entity.entities.worker.person.Location;
import ru.lab.server.model.domain.entity.entities.worker.person.Person;
import ru.lab.server.model.domain.repository.repositories.workerRepository.WorkerRepository;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class ViewCommand extends Command {
  private static final Logger logger = LogManager.getLogger(ViewCommand.class);

  private static final String NAME_PATTERN = "%s:";
  private static final String EMPTY_PATTERN = "\t%s:";
  private static final String PREFIX_PATTERN = "\t%s: %s";

  protected final Locale locale;
  protected final WorkerRepository workerRepository;
  protected final Parser parser;
  protected final DateFormat dateFormat;
  protected final NumberFormat numberFormat;
  protected final StatusFormat statusFormat;
  protected final EyeColorFormat eyeColorFormat;
  protected final HairColorFormat hairColorFormat;
  protected final NumberFormat currencyFormat;

  protected final String workerPrefix;
  protected final String workerIdPrefix;
  protected final String workerOwnerIdPrefix;
  protected final String workerKeyPrefix;
  protected final String workerNamePrefix;
  protected final String workerSalaryPrefix;
  protected final String workerStartDatePrefix;
  protected final String workerEndDatePrefix;
  protected final String workerStatusPrefix;

  protected final String coordinatesPrefix;
  protected final String coordinatesIdPrefix;
  protected final String coordinatesOwnerIdPrefix;
  protected final String coordinatesXPrefix;
  protected final String coordinatesYPrefix;

  protected final String personPrefix;
  protected final String personIdPrefix;
  protected final String personOwnerIdPrefix;
  protected final String personPassportIdPrefix;
  protected final String personEyeColorPrefix;
  protected final String personHairColorPrefix;

  protected final String locationPrefix;
  protected final String locationIdPrefix;
  protected final String locationOwnerIdPrefix;
  protected final String locationXPrefix;
  protected final String locationYPrefix;
  protected final String locationZPrefix;
  protected final String locationNamePrefix;

  public ViewCommand(
      Configuration configuration,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      Locale locale,
      WorkerRepository workerRepository,
      Parser parser) {
    super(configuration, argumentMediator, arguments);
    this.locale = locale;
    this.workerRepository = workerRepository;
    this.parser = parser;
    numberFormat = NumberFormat.getNumberInstance(locale);
    dateFormat = DateFormat.getDateInstance(locale);
    statusFormat = StatusFormat.getStatusInstance(locale);
    eyeColorFormat = EyeColorFormat.getEyeColorInstance(locale);
    hairColorFormat = HairColorFormat.getHairColorInstance(locale);
    currencyFormat = NumberFormat.getCurrencyInstance(locale);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.ViewCommand", locale);

    workerPrefix = resourceBundle.getString("prefixes.worker");
    workerOwnerIdPrefix = resourceBundle.getString("prefixes.worker.ownerId");
    workerIdPrefix = resourceBundle.getString("prefixes.worker.id");
    workerKeyPrefix = resourceBundle.getString("prefixes.worker.key");
    workerNamePrefix = resourceBundle.getString("prefixes.worker.name");
    workerSalaryPrefix = resourceBundle.getString("prefixes.worker.salary");
    workerStartDatePrefix = resourceBundle.getString("prefixes.worker.startDate");
    workerEndDatePrefix = resourceBundle.getString("prefixes.worker.endDate");
    workerStatusPrefix = resourceBundle.getString("prefixes.worker.status");

    coordinatesPrefix = resourceBundle.getString("prefixes.coordinates");
    coordinatesIdPrefix = resourceBundle.getString("prefixes.coordinates.id");
    coordinatesOwnerIdPrefix = resourceBundle.getString("prefixes.coordinates.ownerId");
    coordinatesXPrefix = resourceBundle.getString("prefixes.coordinates.x");
    coordinatesYPrefix = resourceBundle.getString("prefixes.coordinates.y");

    personPrefix = resourceBundle.getString("prefixes.person");
    personIdPrefix = resourceBundle.getString("prefixes.person.id");
    personOwnerIdPrefix = resourceBundle.getString("prefixes.person.ownerId");
    personPassportIdPrefix = resourceBundle.getString("prefixes.person.passportId");
    personEyeColorPrefix = resourceBundle.getString("prefixes.person.eyeColor");
    personHairColorPrefix = resourceBundle.getString("prefixes.person.hairColor");

    locationPrefix = resourceBundle.getString("prefixes.location");
    locationIdPrefix = resourceBundle.getString("prefixes.location.id");
    locationOwnerIdPrefix = resourceBundle.getString("prefixes.location.ownerId");
    locationXPrefix = resourceBundle.getString("prefixes.location.x");
    locationYPrefix = resourceBundle.getString("prefixes.location.y");
    locationZPrefix = resourceBundle.getString("prefixes.location.z");
    locationNamePrefix = resourceBundle.getString("prefixes.location.name");
  }

  protected final String workerToString(Worker worker) {
    StringBuilder stringBuilder = new StringBuilder(String.format(NAME_PATTERN, workerPrefix));

    if (worker == null) {
      return stringBuilder.toString();
    }

    stringBuilder
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, workerIdPrefix, worker.getId()))
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, workerOwnerIdPrefix, worker.getOwnerId()))
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, workerKeyPrefix, worker.getKey()))
        .append(System.lineSeparator());

    if (worker.getName() != null) {
      stringBuilder
          .append(String.format(PREFIX_PATTERN, workerNamePrefix, worker.getName()))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, workerNamePrefix))
          .append(System.lineSeparator());
    }

    String coordinatesString =
        coordinatesToString(worker.getCoordinates())
            .replaceAll(System.lineSeparator(), System.lineSeparator() + '\t');

    stringBuilder
        .append('\t')
        .append(coordinatesString)
        .append(System.lineSeparator())
        .append(
            String.format(
                PREFIX_PATTERN, workerSalaryPrefix, currencyFormat.format(worker.getSalary())))
        .append(System.lineSeparator());

    if (worker.getStartDate() != null) {
      stringBuilder
          .append(
              String.format(
                  PREFIX_PATTERN, workerStartDatePrefix, dateFormat.format(worker.getStartDate())))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, workerStartDatePrefix))
          .append(System.lineSeparator());
    }

    if (worker.getEndDate() != null) {
      stringBuilder
          .append(
              String.format(
                  PREFIX_PATTERN, workerEndDatePrefix, dateFormat.format(worker.getEndDate())))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, workerEndDatePrefix))
          .append(System.lineSeparator());
    }

    if (worker.getStatus() != null) {
      stringBuilder
          .append(
              String.format(
                  PREFIX_PATTERN, workerStatusPrefix, statusFormat.format(worker.getStatus())))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, workerStatusPrefix))
          .append(System.lineSeparator());
    }

    String personString =
        personToString(worker.getPerson())
            .replaceAll(System.lineSeparator(), System.lineSeparator() + '\t');

    stringBuilder.append('\t').append(personString);

    logger.info(() -> "Worker was converted to string.");
    return stringBuilder.toString();
  }

  protected final String coordinatesToString(Coordinates coordinates) {
    StringBuilder stringBuilder = new StringBuilder(String.format(NAME_PATTERN, coordinatesPrefix));

    if (coordinates == null) {
      return stringBuilder.toString();
    }

    stringBuilder
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, coordinatesIdPrefix, coordinates.getId()))
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, coordinatesOwnerIdPrefix, coordinates.getOwnerId()))
        .append(System.lineSeparator());

    stringBuilder
        .append(
            String.format(
                PREFIX_PATTERN, coordinatesXPrefix, numberFormat.format(coordinates.getX())))
        .append(System.lineSeparator());

    if (coordinates.getY() != null) {
      stringBuilder
          .append(
              String.format(
                  PREFIX_PATTERN, coordinatesYPrefix, numberFormat.format(coordinates.getY())))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, coordinatesYPrefix))
          .append(System.lineSeparator());
    }

    logger.info(() -> "Coordinates was converted to string.");
    return stringBuilder.toString();
  }

  protected final String personToString(Person person) {
    StringBuilder stringBuilder = new StringBuilder(String.format(NAME_PATTERN, personPrefix));

    if (person == null) {
      return stringBuilder.toString();
    }

    stringBuilder
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, personIdPrefix, person.getId()))
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, personOwnerIdPrefix, person.getOwnerId()))
        .append(System.lineSeparator());

    if (person.getPassportId() != null) {
      stringBuilder
          .append(String.format(PREFIX_PATTERN, personPassportIdPrefix, person.getPassportId()))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, personPassportIdPrefix))
          .append(System.lineSeparator());
    }

    if (person.getEyeColor() != null) {
      stringBuilder
          .append(
              String.format(
                  PREFIX_PATTERN,
                  personEyeColorPrefix,
                  eyeColorFormat.format(person.getEyeColor())))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, personEyeColorPrefix))
          .append(System.lineSeparator());
    }

    if (person.getHairColor() != null) {
      stringBuilder
          .append(
              String.format(
                  PREFIX_PATTERN,
                  personHairColorPrefix,
                  hairColorFormat.format(person.getHairColor())))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, personHairColorPrefix))
          .append(System.lineSeparator());
    }

    String locationString =
        locationToString(person.getLocation())
            .replaceAll(System.lineSeparator(), System.lineSeparator() + '\t');
    stringBuilder.append('\t').append(locationString);

    logger.info(() -> "Person was converted to string.");
    return stringBuilder.toString();
  }

  protected final String locationToString(Location location) {
    StringBuilder stringBuilder = new StringBuilder(String.format(NAME_PATTERN, locationPrefix));

    if (location == null) {
      return stringBuilder.toString();
    }

    stringBuilder
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, locationIdPrefix, location.getId()))
        .append(System.lineSeparator())
        .append(String.format(PREFIX_PATTERN, locationOwnerIdPrefix, location.getOwnerId()))
        .append(System.lineSeparator());

    if (location.getX() != null) {
      stringBuilder
          .append(
              String.format(PREFIX_PATTERN, locationXPrefix, numberFormat.format(location.getX())))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, locationXPrefix))
          .append(System.lineSeparator());
    }

    stringBuilder
        .append(
            String.format(PREFIX_PATTERN, locationYPrefix, numberFormat.format(location.getY())))
        .append(System.lineSeparator());

    if (location.getZ() != null) {
      stringBuilder
          .append(
              String.format(PREFIX_PATTERN, locationZPrefix, numberFormat.format(location.getZ())))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, locationZPrefix))
          .append(System.lineSeparator());
    }

    if (location.getName() != null) {
      stringBuilder
          .append(String.format(PREFIX_PATTERN, locationNamePrefix, location.getName()))
          .append(System.lineSeparator());
    } else {
      stringBuilder
          .append(String.format(EMPTY_PATTERN, locationNamePrefix))
          .append(System.lineSeparator());
    }

    logger.info(() -> "Location was converted to string.");
    return stringBuilder.toString();
  }
}
