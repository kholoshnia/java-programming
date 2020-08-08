package ru.lab.common;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/** Argument mediator class contains all argument names. */
public final class ArgumentMediator {
  public final String worker;
  public final String workerId;
  public final String workerKey;
  public final String workerName;
  public final String workerSalary;
  public final String workerStartDate;
  public final String workerEndDate;
  public final String workerStatus;

  public final String coordinates;
  public final String coordinatesX;
  public final String coordinatesY;

  public final String person;
  public final String personPassportId;
  public final String personEyeColor;
  public final String personHairColor;

  public final String location;
  public final String locationX;
  public final String locationY;
  public final String locationZ;
  public final String locationName;

  public final String userName;
  public final String userLogin;
  public final String userPassword;

  public final String scriptLine;

  public final String include;

  private final List<String> arguments;

  @Inject
  public ArgumentMediator() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("ArgumentMediator");

    worker = resourceBundle.getString("arguments.worker");
    workerId = resourceBundle.getString("arguments.worker.id");
    workerKey = resourceBundle.getString("arguments.worker.key");
    workerName = resourceBundle.getString("arguments.worker.name");
    workerSalary = resourceBundle.getString("arguments.worker.salary");
    workerStartDate = resourceBundle.getString("arguments.worker.startDate");
    workerEndDate = resourceBundle.getString("arguments.worker.endDate");
    workerStatus = resourceBundle.getString("arguments.worker.status");

    coordinates = resourceBundle.getString("arguments.coordinates");
    coordinatesX = resourceBundle.getString("arguments.coordinates.x");
    coordinatesY = resourceBundle.getString("arguments.coordinates.y");

    person = resourceBundle.getString("arguments.person");
    personPassportId = resourceBundle.getString("arguments.person.passportId");
    personEyeColor = resourceBundle.getString("arguments.person.eyeColor");
    personHairColor = resourceBundle.getString("arguments.person.hairColor");

    location = resourceBundle.getString("arguments.location");
    locationX = resourceBundle.getString("arguments.location.x");
    locationY = resourceBundle.getString("arguments.location.y");
    locationZ = resourceBundle.getString("arguments.location.z");
    locationName = resourceBundle.getString("arguments.location.name");

    userName = resourceBundle.getString("arguments.user.name");
    userLogin = resourceBundle.getString("arguments.user.login");
    userPassword = resourceBundle.getString("arguments.user.password");

    scriptLine = resourceBundle.getString("arguments.scriptLine");

    include = resourceBundle.getString("arguments.include");

    arguments = initArgumentsList();
  }

  private List<String> initArgumentsList() {
    return new ArrayList<String>() {
      {
        if (worker != null) {
          add(worker);
        }
        if (workerId != null) {
          add(workerId);
        }
        if (workerKey != null) {
          add(workerKey);
        }
        if (workerName != null) {
          add(workerName);
        }
        if (workerSalary != null) {
          add(workerSalary);
        }
        if (workerStartDate != null) {
          add(workerStartDate);
        }
        if (workerEndDate != null) {
          add(workerEndDate);
        }
        if (workerStatus != null) {
          add(workerStatus);
        }

        if (coordinates != null) {
          add(coordinates);
        }
        if (coordinatesX != null) {
          add(coordinatesX);
        }
        if (coordinatesY != null) {
          add(coordinatesY);
        }

        if (person != null) {
          add(person);
        }
        if (personPassportId != null) {
          add(personPassportId);
        }
        if (personEyeColor != null) {
          add(personEyeColor);
        }
        if (personHairColor != null) {
          add(personHairColor);
        }

        if (location != null) {
          add(location);
        }
        if (locationX != null) {
          add(locationX);
        }
        if (locationY != null) {
          add(locationY);
        }
        if (locationZ != null) {
          add(locationZ);
        }
        if (locationName != null) {
          add(locationName);
        }

        if (userName != null) {
          add(userName);
        }
        if (userLogin != null) {
          add(userLogin);
        }
        if (userPassword != null) {
          add(userPassword);
        }

        if (scriptLine != null) {
          add(scriptLine);
        }

        if (include != null) {
          add(include);
        }
      }
    };
  }

  public List<String> getArguments() {
    return new ArrayList<>(arguments);
  }

  public boolean contains(String argument) {
    return arguments.contains(argument);
  }
}
