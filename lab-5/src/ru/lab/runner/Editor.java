package ru.lab.runner;

import ru.lab.collection.Collection;
import ru.lab.commands.CommandsHistory;
import ru.lab.console.Input;
import ru.lab.console.Output;
import ru.lab.element.Worker;
import ru.lab.element.WorkerException;
import com.lab.element.dependentClasses.*;
import ru.lab.element.dependentClasses.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/** Editor class contains data to be passed to command */
public final class Editor {
  private Input in;
  private Output out;
  private String value;
  private boolean running;
  private boolean fromFile;
  private String dataFilePath;
  private ru.lab.collection.Collection collection;
  private List<Integer> filesHashes;
  private CommandsHistory commandsHistory;

  public Editor() {
    value = null;
    running = true;
    fromFile = false;
    in = new Input();
    out = new Output();
    collection = new ru.lab.collection.Collection();
    filesHashes = new ArrayList<>();
    commandsHistory = new CommandsHistory();
  }

  /**
   * Sets data file path
   *
   * @param dataFilePath data file path
   */
  public void setDataFilePath(String dataFilePath) {
    this.dataFilePath = dataFilePath;
  }

  /**
   * Returns true if read is from file
   *
   * @return True if read is from file
   */
  public boolean getFromFile() {
    return fromFile;
  }

  /**
   * Sets if read is from file
   *
   * @param fromFile true if from file
   */
  public void setFromFile(boolean fromFile) {
    this.fromFile = fromFile;
  }

  /**
   * Returns entered value
   *
   * @return Entered value
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value entered
   *
   * @param value New entered value
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Returns files hashes to catch recursion
   *
   * @return Files hashes
   */
  public List<Integer> getFilesHashes() {
    return filesHashes;
  }

  /**
   * Returns input
   *
   * @return Input
   */
  public Input getIn() {
    return in;
  }

  /**
   * Sets input
   *
   * @param in new input
   */
  public void setIn(Input in) {
    this.in = in;
  }

  /**
   * Returns output
   *
   * @return Output
   */
  public Output getOut() {
    return out;
  }

  /** Clears files hashes list */
  public void clearFilesHashes() {
    filesHashes.clear();
  }

  /**
   * Returns running parameter Program stops if running parameter become false
   *
   * @return Running parameter
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Sets running parameter
   *
   * @param running new running parameter value
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

  /**
   * Returns collection to work with
   *
   * @return Collection to work with
   */
  public ru.lab.collection.Collection getCollection() {
    return collection;
  }

  /**
   * Returns history of correctly executed commands
   *
   * @return History of correctly executed commands
   */
  public CommandsHistory getCommandHistory() {
    return commandsHistory;
  }

  /**
   * Loads collection from the file
   *
   * @return Response about load correctness
   */
  public Response load() {
    List<String> response = new ArrayList<>();
    ru.lab.collection.Collection temp = new Collection();
    Input in = new Input();
    String input;
    try {
      in.setReader(new BufferedReader(new InputStreamReader(new FileInputStream(dataFilePath))));
      in.readLine();
      in.readLine();
      while (!Objects.requireNonNull(in.readLine()).equals("</workers>")) {
        Worker worker = new Worker();
        int key =
            Integer.parseInt(Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", ""));
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          worker.setId(Integer.parseInt(input));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          worker.setName(input);
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          worker.setCreationDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        in.readLine();
        Coordinates coordinates = new Coordinates();
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          coordinates.setX(Double.parseDouble(input));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          coordinates.setY(Double.parseDouble(input));
        }
        worker.setCoordinates(coordinates);
        in.readLine();
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          worker.setSalary(Double.parseDouble(input));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          worker.setStartDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(input));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          worker.setEndDate(
              LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          worker.setStatus(Worker.Status.valueOf(input));
        }
        in.readLine();
        Person person = new Person();
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          person.setEyeColor(Person.Color.valueOf(input));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          person.setHairColor(Person.Color.valueOf(input));
        }
        in.readLine();
        Location location = new Location();
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          location.setX(Long.parseLong(input));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          location.setY(Long.parseLong(input));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          location.setZ(Double.parseDouble(input));
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          location.setName(input);
        }
        person.setLocation(location);
        in.readLine();
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          person.setPassportID(input);
        }
        input = Objects.requireNonNull(in.readLine()).replaceAll("(?s)\t*<.*?>", "");
        if (!input.equals("null")) {
          worker.setPerson(person);
        }
        in.readLine();
        temp.put(key, worker);
      }
    } catch (FileNotFoundException e) {
      response.add("Файл не найден");
      return new Response(false, response, Response.Types.ERROR);
    } catch (NullPointerException ex) {
      response.add("Неожиданный конец файла");
      return new Response(false, response, Response.Types.ERROR);
    } catch (ParseException | IllegalArgumentException ex) {
      response.add("Неверный формат входных данных");
      return new Response(false, response, Response.Types.ERROR);
    } catch (CoordinatesException | PersonException | LocationException | WorkerException ex) {
      response.add("Неверный формат входных данных: " + ex.getMessage());
      return new Response(false, response, Response.Types.ERROR);
    } catch (Exception ex) {
      response.add("Ошибка: " + ex.getMessage());
      return new Response(false, response, Response.Types.ERROR);
    }
    collection = temp;
    response.add("Данные загружены");
    return new Response(true, response, Response.Types.CORRECT);
  }

  /**
   * Saves collection to the file
   *
   * @return Response about save correctness
   */
  public Response save() {
    List<String> response = new ArrayList<>();
    try {
      BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(dataFilePath));
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
      writer.write("<workers>\n".getBytes());
      for (Map.Entry<Integer, Worker> el : collection.getEntrySet()) {
        writer.write("\t<element>\n".getBytes());
        writer.write(("\t\t<key>" + el.getKey() + "</key>\n").getBytes());
        writer.write(("\t\t<id>" + el.getValue().getId() + "</id>\n").getBytes());
        writer.write(("\t\t<name>" + el.getValue().getName() + "</name>\n").getBytes());
        writer.write(
            ("\t\t<creationDate>"
                    + el.getValue()
                        .getCreationDate()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    + "</creationDate>\n")
                .getBytes());
        writer.write("\t\t<coordinates>\n".getBytes());
        writer.write(("\t\t\t<x>" + el.getValue().getCoordinates().getX() + "</x>\n").getBytes());
        writer.write(("\t\t\t<y>" + el.getValue().getCoordinates().getY() + "</y>\n").getBytes());
        writer.write("\t\t</coordinates>\n".getBytes());
        if (el.getValue().getSalary() != 0.0) {
          writer.write(("\t\t<salary>" + el.getValue().getSalary() + "</salary>\n").getBytes());
        } else {
          writer.write(("\t\t<salary>null</salary>\n").getBytes());
        }
        writer.write(
            ("\t\t<startDate>"
                    + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        .format(el.getValue().getStartDate())
                    + "</startDate>\n")
                .getBytes());
        if (el.getValue().getEndDate() != null) {
          writer.write(
              ("\t\t<endDate>"
                      + el.getValue()
                          .getEndDate()
                          .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                      + "</endDate>\n")
                  .getBytes());
        } else {
          writer.write(("\t\t<endDate>null</endDate>\n").getBytes());
        }
        writer.write(("\t\t<status>" + el.getValue().getStatus() + "</status>\n").getBytes());
        writer.write("\t\t<person>\n".getBytes());
        writer.write(
            ("\t\t\t<eyeColor>" + el.getValue().getPerson().getEyeColor() + "</eyeColor>\n")
                .getBytes());
        writer.write(
            ("\t\t\t<hairColor>" + el.getValue().getPerson().getHairColor() + "</hairColor>\n")
                .getBytes());
        writer.write("\t\t\t<location>\n".getBytes());
        writer.write(
            ("\t\t\t\t<x>" + el.getValue().getPerson().getLocation().getX() + "</x>\n").getBytes());
        writer.write(
            ("\t\t\t\t<y>" + el.getValue().getPerson().getLocation().getY() + "</y>\n").getBytes());
        writer.write(
            ("\t\t\t\t<z>" + el.getValue().getPerson().getLocation().getZ() + "</z>\n").getBytes());
        writer.write(
            ("\t\t\t\t<name>" + el.getValue().getPerson().getLocation().getName() + "</name>\n")
                .getBytes());
        writer.write("\t\t\t</location>\n".getBytes());
        writer.write(
            ("\t\t\t<passportID>" + el.getValue().getPerson().getPassportID() + "</passportID>\n")
                .getBytes());
        writer.write("\t\t</person>\n".getBytes());
        writer.write("\t</element>\n".getBytes());
      }
      writer.write("</workers>\n".getBytes());
      writer.close();
      response.add("Коллекция записана в файл");
      return new Response(true, response, Response.Types.CORRECT);
    } catch (IOException ex) {
      response.add("Ошибка записи в файл");
    }
    return new Response(false, response, Response.Types.ERROR);
  }

  /**
   * Prints a message, receives the value and checks its correctness
   *
   * @param message a message to print
   * @param in input
   * @param out output
   * @param required whether the parameter is required or not
   * @param lambda lambda expression to check input correctness
   * @return True if value is set or may be skipped
   */
  public boolean inputValue(
      String message, Input in, Output out, boolean required, Predicate<String> lambda) {
    while (true) {
      out.print(message);
      String input = in.readLine();
      if (input == null) {
        out.print(System.lineSeparator() + "Неверный формат. ", Response.Types.FAIL.getColor());
      } else if (input.equals("cancel")) {
        out.println("Отмена создания нового элемента", Response.Types.UNUSUAL.getColor());
        return true;
      } else if (input.equals("")) {
        if (required) {
          out.print("Обязательный параметр. ", Response.Types.FAIL.getColor());
        } else {
          return false;
        }
      } else if (lambda.test(input)) {
        if (fromFile) {
          out.println(input);
        }
        return false;
      }
    }
  }

  /**
   * Creates new element of the collection
   *
   * @return New element if add parameter is true else returns void
   */
  public Worker create() {
    out.println(
        "Введите необходимые данные или нажмите Enter для пропуска параметра, если это возможно (введите cancel для отмены)");
    Worker worker = new Worker();

    if (inputValue(
        "Введите имя: ",
        in,
        out,
        true,
        input -> {
          if (Pattern.matches("[А-Яа-яA-Za-z- ]+$", input)) {
            worker.setName(input);
            return true;
          } else {
            out.print("Неверный формат. ", Response.Types.ERROR.getColor());
          }
          return false;
        })) {
      return null;
    }

    out.println("Введите координаты: ");
    Coordinates coordinates = new Coordinates();

    if (inputValue(
        "Введите x: ",
        in,
        out,
        false,
        input -> {
          try {
            coordinates.setX(Long.parseLong(input));
            return true;
          } catch (CoordinatesException ex) {
            out.print(ex.getMessage() + ". ", Response.Types.ERROR.getColor());
            return false;
          } catch (NumberFormatException ex) {
            out.print("Неверное значение. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите y: ",
        in,
        out,
        true,
        input -> {
          try {
            coordinates.setY(Long.parseLong(input));
            return true;
          } catch (CoordinatesException ex) {
            out.print(ex.getMessage() + ". ", Response.Types.ERROR.getColor());
            return false;
          } catch (NumberFormatException ex) {
            out.print("Неверное значение. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    worker.setCoordinates(coordinates);

    if (inputValue(
        "Введите зарплату: ",
        in,
        out,
        false,
        input -> {
          try {
            worker.setSalary(Double.parseDouble(input));
            return true;
          } catch (WorkerException ex) {
            out.print(ex.getMessage() + ". ", Response.Types.ERROR.getColor());
            return false;
          } catch (NumberFormatException ex) {
            out.print("Неверное значение. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите дату начала (dd-MM-yyyy HH:mm:ss): ",
        in,
        out,
        true,
        input -> {
          try {
            worker.setStartDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(input));
            return true;
          } catch (ParseException ex) {
            out.print("Неверный формат даты. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите дату окончания (dd-MM-yyyy HH:mm:ss): ",
        in,
        out,
        false,
        input -> {
          try {
            worker.setEndDate(
                LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            return true;
          } catch (DateTimeParseException ex) {
            out.print("Неверный формат даты. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите статус " + Arrays.toString(Worker.Status.values()) + ": ",
        in,
        out,
        false,
        input -> {
          try {
            worker.setStatus(Worker.Status.valueOf(input));
            return true;
          } catch (IllegalArgumentException ex) {
            out.print("Неверный статус. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    out.println("Введите личные данные: ");
    Person person = new Person();

    if (inputValue(
        "Введите цвет глаз " + Arrays.toString(Person.Color.values()) + ": ",
        in,
        out,
        false,
        input -> {
          try {
            person.setEyeColor(Person.Color.valueOf(input));
            return true;
          } catch (IllegalArgumentException ex) {
            out.print("Неверный цвет глаз. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите цвет волос " + Arrays.toString(Person.Color.values()) + ": ",
        in,
        out,
        true,
        input -> {
          try {
            person.setHairColor(Person.Color.valueOf(input));
            return true;
          } catch (IllegalArgumentException ex) {
            out.print("Неверный цвет волос. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    out.println("Введите местоположение: ");
    Location location = new Location();

    if (inputValue(
        "Введите x: ",
        in,
        out,
        true,
        input -> {
          try {
            location.setX(Long.parseLong(input));
            return true;
          } catch (NumberFormatException ex) {
            out.print("Неверное значение. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите y: ",
        in,
        out,
        false,
        input -> {
          try {
            location.setY(Long.parseLong(input));
            return true;
          } catch (NumberFormatException ex) {
            out.print("Неверное значение. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите z: ",
        in,
        out,
        true,
        input -> {
          try {
            location.setZ(Double.parseDouble(input));
            return true;
          } catch (NumberFormatException ex) {
            out.print("Неверное значение. ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите название: ",
        in,
        out,
        false,
        input -> {
          try {
            location.setName(input);
            return true;
          } catch (LocationException ex) {
            out.print(ex.getMessage());
            return false;
          }
        })) {
      return null;
    }

    person.setLocation(location);

    if (inputValue(
        "Введите ID пасспорта: ",
        in,
        out,
        true,
        input -> {
          try {
            person.setPassportID(input);
            return true;
          } catch (PersonException ex) {
            out.print(ex.getMessage() + ". ", Response.Types.ERROR.getColor());
            return false;
          }
        })) {
      return null;
    }

    worker.setPerson(person);
    worker.setId(worker.hashCode() & 0xfffffff);
    worker.setCreationDate(LocalDate.now());

    return worker;
  }
}
