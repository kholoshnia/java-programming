package ru.lab.server.model.domain.dto.dtos;

import ru.lab.server.model.domain.dto.DTO;
import ru.lab.server.model.domain.entity.entities.worker.Coordinates;
import ru.lab.server.model.domain.entity.entities.worker.Status;
import ru.lab.server.model.domain.entity.entities.worker.Worker;
import ru.lab.server.model.domain.entity.entities.worker.person.Person;
import ru.lab.server.model.domain.entity.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public final class WorkerDTO implements DTO<Worker> {
  public static final String TABLE_NAME = "workers";

  public static final String ID_COLUMN = "id";
  public static final String OWNER_ID_COLUMN = "owner_id";
  public static final String KEY_COLUMN = "key";
  public static final String NAME_COLUMN = "name";
  public static final String COORDINATES_COLUMN = "coordinates";
  public static final String CREATION_DATE_COLUMN = "creation_date";
  public static final String SALARY_COLUMN = "salary";
  public static final String START_DATE_COLUMN = "start_date";
  public static final String END_DATE_COLUMN = "end_date";
  public static final String STATUS_COLUMN = "status";
  public static final String PERSON_COLUMN = "person";

  public final long id;
  public final long ownerId;
  public final int key;
  public final String name;
  public final CoordinatesDTO coordinatesDTO;
  public final LocalDate creationDate;
  public final double salary;
  public final Date startDate;
  public final LocalDateTime endDate;
  public final Status status;
  public final PersonDTO personDTO;

  public WorkerDTO(
      long id,
      long ownerId,
      int key,
      String name,
      CoordinatesDTO coordinatesDTO,
      LocalDate creationDate,
      double salary,
      Date startDate,
      LocalDateTime endDate,
      Status status,
      PersonDTO personDTO) {
    this.id = id;
    this.ownerId = ownerId;
    this.key = key;
    this.name = name;
    this.coordinatesDTO = coordinatesDTO;
    this.creationDate = creationDate;
    this.salary = salary;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.personDTO = personDTO;
  }

  @Override
  public Worker toEntity() throws ValidationException {
    Coordinates coordinates;

    if (coordinatesDTO != null) {
      coordinates = coordinatesDTO.toEntity();
    } else {
      coordinates = null;
    }

    Person person;

    if (personDTO != null) {
      person = personDTO.toEntity();
    } else {
      person = null;
    }

    return new Worker(
        id,
        ownerId,
        key,
        name,
        coordinates,
        creationDate,
        salary,
        startDate,
        endDate,
        status,
        person);
  }

  @Override
  public String toString() {
    return "WorkerDTO{"
        + "id="
        + id
        + ", ownerId="
        + ownerId
        + ", key="
        + key
        + ", name='"
        + name
        + '\''
        + ", coordinatesDTO="
        + coordinatesDTO
        + ", creationDate="
        + creationDate
        + ", salary="
        + salary
        + ", startDate="
        + startDate
        + ", endDate="
        + endDate
        + ", status="
        + status
        + ", personDTO="
        + personDTO
        + '}';
  }
}
