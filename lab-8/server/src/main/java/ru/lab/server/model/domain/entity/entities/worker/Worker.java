package ru.lab.server.model.domain.entity.entities.worker;

import ru.lab.server.model.domain.dto.dtos.CoordinatesDTO;
import ru.lab.server.model.domain.dto.dtos.PersonDTO;
import ru.lab.server.model.domain.dto.dtos.WorkerDTO;
import ru.lab.server.model.domain.entity.Entity;
import ru.lab.server.model.domain.entity.entities.worker.person.Person;
import ru.lab.server.model.domain.entity.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public final class Worker implements Comparable<Worker>, Cloneable, Entity {
  public static final long DEFAULT_ID = 0L;
  public static final long DEFAULT_OWNER_ID = 0L;
  public static final int DEFAULT_KEY = 0;

  private static final String WRONG_ID_EXCEPTION;
  private static final String WRONG_OWNER_ID_EXCEPTION;
  private static final String WRONG_KEY_EXCEPTION;
  private static final String WRONG_NAME_EXCEPTION;
  private static final String WRONG_COORDINATES_EXCEPTION;
  private static final String WRONG_CREATION_DATE_EXCEPTION;
  private static final String WRONG_SALARY_EXCEPTION;
  private static final String WRONG_START_DATE_EXCEPTION;

  static {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("internal.Worker");

    WRONG_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongId");
    WRONG_OWNER_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongOwnerId");
    WRONG_KEY_EXCEPTION = resourceBundle.getString("exceptions.wrongKey");
    WRONG_NAME_EXCEPTION = resourceBundle.getString("exceptions.wrongName");
    WRONG_COORDINATES_EXCEPTION = resourceBundle.getString("exceptions.wrongCoordinates");
    WRONG_CREATION_DATE_EXCEPTION = resourceBundle.getString("exceptions.wrongCreationDate");
    WRONG_SALARY_EXCEPTION = resourceBundle.getString("exceptions.wrongSalary");
    WRONG_START_DATE_EXCEPTION = resourceBundle.getString("exceptions.wrongStartDate");
  }

  private long id;
  private long ownerId;
  private int key;
  private String name;
  private Coordinates coordinates;
  private LocalDate creationDate;
  private double salary;
  private Date startDate;
  private LocalDateTime endDate;
  private Status status;
  private Person person;

  public Worker(
      long id,
      long ownerId,
      int key,
      String name,
      Coordinates coordinates,
      LocalDate creationDate,
      double salary,
      Date startDate,
      LocalDateTime endDate,
      Status status,
      Person person)
      throws ValidationException {
    checkId(id);
    this.id = id;

    checkOwnerId(ownerId);
    this.ownerId = ownerId;

    checkKey(key);
    this.key = key;

    checkName(name);
    this.name = name;

    checkCoordinates(coordinates);
    this.coordinates = coordinates;

    checkCreationDate(creationDate);
    this.creationDate = creationDate;

    checkSalary(salary);
    this.salary = salary;

    checkStartDate(startDate);
    this.startDate = startDate;

    this.endDate = endDate;
    this.status = status;
    this.person = person;
  }

  @Override
  public WorkerDTO toDTO() {
    CoordinatesDTO coordinatesDTO;

    if (coordinates != null) {
      coordinatesDTO = coordinates.toDTO();
    } else {
      coordinatesDTO = null;
    }

    PersonDTO personDTO;

    if (person != null) {
      personDTO = person.toDTO();
    } else {
      personDTO = null;
    }

    return new WorkerDTO(
        id,
        ownerId,
        key,
        name,
        coordinatesDTO,
        creationDate,
        salary,
        startDate,
        endDate,
        status,
        personDTO);
  }

  public final long getId() {
    return id;
  }

  public final void setId(long id) throws ValidationException {
    checkId(id);
    this.id = id;
  }

  private void checkId(long id) throws ValidationException {
    if (id > 0 || id == DEFAULT_ID) {
      return;
    }

    throw new ValidationException(WRONG_ID_EXCEPTION);
  }

  public final long getOwnerId() {
    return ownerId;
  }

  public final void setOwnerId(long ownerId) throws ValidationException {
    checkOwnerId(ownerId);
    this.ownerId = ownerId;
  }

  private void checkOwnerId(long ownerId) throws ValidationException {
    if (ownerId > 0 || ownerId == DEFAULT_OWNER_ID) {
      return;
    }

    throw new ValidationException(WRONG_OWNER_ID_EXCEPTION);
  }

  public final int getKey() {
    return key;
  }

  public final void setKey(int key) throws ValidationException {
    checkKey(key);
    this.key = key;
  }

  private void checkKey(int key) throws ValidationException {
    if (key > 0 || key == DEFAULT_KEY) {
      return;
    }

    throw new ValidationException(WRONG_KEY_EXCEPTION);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) throws ValidationException {
    checkName(name);
    this.name = name;
  }

  private void checkName(String name) throws ValidationException {
    if (name != null && !name.isEmpty()) {
      return;
    }

    throw new ValidationException(WRONG_NAME_EXCEPTION);
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) throws ValidationException {
    checkCoordinates(coordinates);
    this.coordinates = coordinates;
  }

  private void checkCoordinates(Coordinates coordinates) throws ValidationException {
    if (coordinates != null) {
      return;
    }
    throw new ValidationException(WRONG_COORDINATES_EXCEPTION);
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) throws ValidationException {
    checkCreationDate(creationDate);
    this.creationDate = creationDate;
  }

  private void checkCreationDate(LocalDate creationDate) throws ValidationException {
    if (creationDate != null) {
      return;
    }

    throw new ValidationException(WRONG_CREATION_DATE_EXCEPTION);
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) throws ValidationException {
    checkSalary(salary);
    this.salary = salary;
  }

  private void checkSalary(double salary) throws ValidationException {
    if (salary > 0.0) {
      return;
    }

    throw new ValidationException(WRONG_SALARY_EXCEPTION);
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) throws ValidationException {
    checkStartDate(startDate);
    this.startDate = startDate;
  }

  private void checkStartDate(Date startDate) throws ValidationException {
    if (startDate != null) {
      return;
    }

    throw new ValidationException(WRONG_START_DATE_EXCEPTION);
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  @Override
  public int compareTo(Worker worker) {
    return Double.compare(salary, worker.salary);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Worker worker = (Worker) o;
    return id == worker.id
        && ownerId == worker.ownerId
        && key == worker.key
        && Double.compare(worker.salary, salary) == 0
        && Objects.equals(name, worker.name)
        && Objects.equals(coordinates, worker.coordinates)
        && Objects.equals(creationDate, worker.creationDate)
        && Objects.equals(startDate, worker.startDate)
        && Objects.equals(endDate, worker.endDate)
        && status == worker.status
        && Objects.equals(person, worker.person);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
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
  public Worker clone() {
    try {
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
    } catch (ValidationException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return "Worker{"
        + "id="
        + id
        + ", ownerId="
        + ownerId
        + ", key="
        + key
        + ", name='"
        + name
        + '\''
        + ", coordinates="
        + coordinates
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
        + ", person="
        + person
        + '}';
  }
}
