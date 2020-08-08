package ru.lab.common.element;

import ru.lab.common.element.dependencies.Coordinates;
import ru.lab.common.element.dependencies.Person;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/** Element of the collection */
public class Worker implements Comparable<Worker>, Serializable {
  private int id;
  private String name;
  private Status status;
  private Person person;
  private double salary;
  private Date startDate;
  private LocalDateTime endDate;
  private LocalDate creationDate;
  private Coordinates coordinates;

  /**
   * Worker constructor
   *
   * @param name name
   * @param status status
   * @param person person
   * @param salary salary
   * @param startDate start date
   * @param endDate end date
   * @param coordinates coordinates
   * @throws WorkerException is thrown if the value is wrong
   */
  public Worker(
      String name,
      Status status,
      Person person,
      double salary,
      Date startDate,
      LocalDateTime endDate,
      Coordinates coordinates)
      throws WorkerException {
    setName(name);
    setStatus(status);
    setPerson(person);
    setSalary(salary);
    setStartDate(startDate);
    setEndDate(endDate);
    setCoordinates(coordinates);
  }

  /** Empty constructor */
  public Worker() {}

  /**
   * Returns id
   *
   * @return Id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets id
   *
   * @param id new id
   * @throws WorkerException is thrown if the value is wrong (value should be greater than 0 and
   *     should not be null (0))
   */
  public void setId(int id) throws WorkerException {
    if (id == 0) {
      throw new WorkerException("Значечние поля id не может быть null (0)");
    } else if (id < 0) {
      throw new WorkerException("Значечние поля id должно быть больше 0");
    } else {
      this.id = id;
    }
  }

  /** Generates id */
  public void generateId() {
    id = hashCode() & 0xfffffff;
  }

  /**
   * Returns name
   *
   * @return Name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name
   *
   * @param name new name
   * @throws WorkerException is thrown if the value is wrong (value should not be empty string or
   *     null)
   */
  public void setName(String name) throws WorkerException {
    if (name == null) {
      throw new WorkerException("Значечние поля name не может быть null");
    } else if (name.equals("")) {
      throw new WorkerException("Имя не может быть пустой строкой");
    } else {
      this.name = name;
    }
  }

  /**
   * Returns person
   *
   * @return Person
   */
  public Person getPerson() {
    return person;
  }

  /**
   * Sets person
   *
   * @param person new person
   * @throws WorkerException is thrown if value is wrong (value should not be null)
   */
  public void setPerson(Person person) throws WorkerException {
    if (person == null) {
      throw new WorkerException("Значечние поля person не может быть null");
    } else {
      this.person = person;
    }
  }

  /**
   * Returns status
   *
   * @return Status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Sets status
   *
   * @param status new status
   * @throws WorkerException is thrown if value is wrong (value should not be null)
   */
  public void setStatus(Status status) throws WorkerException {
    if (status == null) {
      throw new WorkerException("Значение поля status не может быть null");
    } else {
      this.status = status;
    }
  }

  /**
   * Returns salary
   *
   * @return Salary
   */
  public double getSalary() {
    return salary;
  }

  /**
   * Sets salary
   *
   * @param salary new salary
   * @throws WorkerException is thrown if value is wrong (value should mre greater than 0)
   */
  public void setSalary(double salary) throws WorkerException {
    if (salary <= 0.0) {
      throw new WorkerException("Значение зарплаты должно быть больше 0");
    } else {
      this.salary = salary;
    }
  }

  /**
   * Returns start date
   *
   * @return Start date
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * Sets start date
   *
   * @param startDate new start date
   * @throws WorkerException is thrown if the value is wrong (value should not be empty string)
   */
  public void setStartDate(Date startDate) throws WorkerException {
    if (startDate == null) {
      throw new WorkerException("Значение поля startDate не может быть null");
    } else {
      this.startDate = startDate;
    }
  }

  /**
   * Returns end date
   *
   * @return End date
   */
  public LocalDateTime getEndDate() {
    return endDate;
  }

  /**
   * Sets end date
   *
   * @param endDate new end date
   * @throws WorkerException is thrown if value is wrong (value should not be null)
   */
  public void setEndDate(LocalDateTime endDate) throws WorkerException {
    if (endDate == null) {
      throw new WorkerException("Значение поля endDate не может быть null");
    } else {
      this.endDate = endDate;
    }
  }

  /**
   * Returns creation date
   *
   * @return Creation date
   */
  public LocalDate getCreationDate() {
    return creationDate;
  }

  /**
   * Sets creation date
   *
   * @param creationDate new coordinates
   * @throws WorkerException is thrown if value is wrong (value should not be null)
   */
  public void setCreationDate(LocalDate creationDate) throws WorkerException {
    if (creationDate == null) {
      throw new WorkerException("Значение поля creationDate не может быть null");
    } else {
      this.creationDate = creationDate;
    }
  }

  /** Generate creation date */
  public void generateCreationDate() {
    this.creationDate = LocalDate.now();
  }

  /**
   * Returns coordinates
   *
   * @return Coordinates
   */
  public Coordinates getCoordinates() {
    return coordinates;
  }

  /**
   * Sets coordinates
   *
   * @param coordinates new coordinates
   * @throws WorkerException is thrown if value is wrong (value should not be null)
   */
  public void setCoordinates(Coordinates coordinates) throws WorkerException {
    if (coordinates == null) {
      throw new WorkerException("Значение поля coordinates не может быть null");
    } else {
      this.coordinates = coordinates;
    }
  }

  /**
   * Compares by salary
   *
   * @param o other worker to compare
   * @return Result of comparison
   */
  @Override
  public int compareTo(Worker o) {
    return Double.compare(salary, o.salary);
  }

  /**
   * Returns a string representation of the object. In general, the {@code toString} method returns
   * a string that "textually represents" this object. The result should be a concise but
   * informative representation that is easy for a person to read. It is recommended that all
   * subclasses override this method.
   *
   * <p>The {@code toString} method for class {@code Object} returns a string consisting of the name
   * of the class of which the object is an instance, the at-sign character `{@code @}', and the
   * unsigned hexadecimal representation of the hash code of the object. In other words, this method
   * returns a string equal to the value of:
   *
   * <blockquote>
   *
   * <pre>
   * getClass().getName() + '@' + Integer.toHexString(hashCode())
   * </pre>
   *
   * </blockquote>
   *
   * @return a string representation of the object { id: value, name: 'value', status: value,
   *     person: value, salary: value, * startDate: value, endDate: value, creationDate: value,
   *     coordinates: value string }.
   */
  @Override
  public String toString() {
    String salaryString = "null";
    if (salary != 0.0) {
      salaryString = String.valueOf(salary);
    }
    String endDateString = "null";
    if (endDate != null) {
      endDateString = endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    String startDateString = "null"; // TODO: Check all null
    if (startDate != null) {
      startDateString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(startDate);
    }
    String creationDateString = "null";
    if (startDate != null) {
      creationDateString = creationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    String personString = "null";
    if (person != null) {
      personString =
          Arrays.stream(person.toString().split(System.lineSeparator()))
              .skip(0)
              .collect(Collectors.joining(System.lineSeparator() + "\t"));
    }
    String coordinatesString = "null";
    if (coordinates != null) {
      coordinatesString =
          Arrays.stream(coordinates.toString().split(System.lineSeparator()))
              .skip(0)
              .collect(Collectors.joining(System.lineSeparator() + '\t'));
    }
    return "{"
        + System.lineSeparator()
        + "\tid: "
        + id
        + ','
        + System.lineSeparator()
        + "\tname: '"
        + name
        + '\''
        + ','
        + System.lineSeparator()
        + "\tstatus: "
        + status
        + ','
        + System.lineSeparator()
        + "\tperson: "
        + personString
        + ','
        + System.lineSeparator()
        + "\tsalary: "
        + salaryString
        + ','
        + System.lineSeparator()
        + "\tstartDate: "
        + startDateString
        + ','
        + System.lineSeparator()
        + "\tendDate: "
        + endDateString
        + ','
        + System.lineSeparator()
        + "\tcreationDate: "
        + creationDateString
        + ','
        + System.lineSeparator()
        + "\tcoordinates: "
        + coordinatesString
        + System.lineSeparator()
        + "}";
  }

  /**
   * Return true if objects are equal
   *
   * @param o other object
   * @return True if object are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Worker worker = (Worker) o;
    return id == worker.id
        && Double.compare(worker.salary, salary) == 0
        && Objects.equals(name, worker.name)
        && status == worker.status
        && Objects.equals(person, worker.person)
        && Objects.equals(startDate, worker.startDate)
        && Objects.equals(endDate, worker.endDate)
        && Objects.equals(creationDate, worker.creationDate)
        && Objects.equals(coordinates, worker.coordinates);
  }

  /**
   * Returns hash code
   *
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(
        id, name, coordinates, creationDate, salary, startDate, endDate, status, person);
  }

  /** Enum for status */
  public enum Status {
    FIRED,
    HIRED,
    RECOMMENDED_FOR_PROMOTION
  }
}
