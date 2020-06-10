package com.lab.element;

import com.lab.element.dependentClasses.Coordinates;
import com.lab.element.dependentClasses.Person;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/** Element of the collection */
public class Worker implements Comparable<Worker> {
  private int id; // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
  // Значение этого поля должно генерироваться автоматически
  private String name; // Поле не может быть null, Строка не может быть пустой
  private Status status; // Поле может быть null
  private Person person; // Поле может быть null
  private double salary; // Значение поля должно быть больше 0
  private Date startDate; // Поле не может быть null
  private LocalDateTime endDate; // Поле может быть null
  private LocalDate
      creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
  // автоматически
  private Coordinates coordinates; // Поле не может быть null

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) throws WorkerException {
    if (salary <= 0.0) {
      throw new WorkerException("Значение зарплаты должно быть больше 0");
    } else {
      this.salary = salary;
    }
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public int compareTo(Worker other) {
    return Double.compare(salary, other.salary);
  }

  @Override
  public String toString() {
    String endDateString = "null";
    if (endDate != null) {
      endDateString = endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    String salaryString = "null";
    if (salary != 0.0) {
      salaryString = String.valueOf(salary);
    }
    return "id: "
        + id
        + ", name: '"
        + name
        + '\''
        + ", status: "
        + status
        + ", person: "
        + person
        + ", salary: "
        + salaryString
        + ", startDate: "
        + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(startDate)
        + ", endDate: "
        + endDateString
        + ", creationDate: "
        + creationDate
        + ", coordinates: "
        + coordinates;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, name, coordinates, creationDate, salary, startDate, endDate, status, person);
  }

  public enum Status {
    FIRED,
    HIRED,
    RECOMMENDED_FOR_PROMOTION
  }
}
