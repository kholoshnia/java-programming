package ru.lab.common.generators;

import ru.lab.common.element.Worker;
import ru.lab.common.element.WorkerException;
import com.lab.common.element.dependencies.*;
import ru.lab.common.element.dependencies.*;
import ru.lab.common.io.Input;
import ru.lab.common.io.InputException;
import ru.lab.common.io.Output;
import ru.lab.common.io.OutputException;
import ru.lab.common.reply.Reply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Pattern;

/** Creator class creates element using input */
public class Creator {
  Input input;
  Output output;

  /**
   * Creator constructor
   *
   * @param input console reader
   * @param output console writer
   */
  public Creator(Input input, Output output) {
    this.input = input;
    this.output = output;
  }

  /**
   * Prints a message, receives the value and checks its correctness
   *
   * @param message a message to print
   * @param required whether the parameter is required or not
   * @param lambda lambda expression to check input correctness
   * @return True if value is set or may be skipped
   */
  public boolean inputValue(String message, boolean required, Function<String, Reply> lambda)
      throws CreatorException, OutputException, InputException {
    while (true) {
      output.write(message);
      String input = null;
      input = this.input.readLine();
      if (input == null) {
        output.write(System.lineSeparator() + "Неверный формат. ", Reply.Types.WARNING.getColor());
      } else if (input.equals("cancel")) {
        output.writeln("Отмена создания нового элемента", Reply.Types.FINE.getColor());
        return true;
      } else if (input.equals("")) {
        if (required) {
          output.write("Обязательный параметр. ", Reply.Types.WARNING.getColor());
        } else {
          return false;
        }
      } else {
        Reply reply = lambda.apply(input);
        if (reply.isCorrect()) {
          return false;
        }
        output.write(reply.getString(), reply.getType().getColor());
        return true;
      }
    }
  }

  /**
   * Creates new element of the collection
   *
   * @return New element if add parameter is true else returns void
   */
  public Worker create() throws CreatorException, InputException, OutputException {
    try {
      output.writeln(
          "Введите необходимые данные или нажмите Enter для пропуска параметра, если это возможно (введите cancel для отмены)");
    } catch (OutputException e) {
      e.printStackTrace();
    }
    Worker worker = new Worker();

    if (inputValue(
        "Введите имя: ",
        true,
        input -> {
          if (Pattern.matches("[А-Яа-яA-Za-z- ]+$", input)) {
            try {
              worker.setName(input);
              return new Reply(true, "Успешно", Reply.Types.FINEST);
            } catch (WorkerException e) {
              return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
            }
          }
          return new Reply(false, "Неверный формат. ", Reply.Types.SEVERE);
        })) {
      return null;
    }

    output.writeln("Введите координаты: ");
    Coordinates coordinates = new Coordinates();

    if (inputValue(
        "Введите x: ",
        false,
        input -> {
          try {
            coordinates.setX(Long.parseLong(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (CoordinatesException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (NumberFormatException e) {
            return new Reply(false, "Неверный формат. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите y: ",
        true,
        input -> {
          try {
            coordinates.setY(Long.parseLong(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (CoordinatesException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (NumberFormatException e) {
            return new Reply(false, "Неверный формат. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    try {
      worker.setCoordinates(coordinates);
    } catch (WorkerException e) {
      output.writeln(e.getMessage() + ". ", Reply.Types.SEVERE.getColor());
      output.writeln("Отмена создания нового элемента");
      return null;
    }

    if (inputValue(
        "Введите зарплату: ",
        false,
        input -> {
          try {
            worker.setSalary(Double.parseDouble(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (WorkerException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (NumberFormatException e) {
            return new Reply(false, "Неверный формат. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите дату начала (dd-MM-yyyy HH:mm:ss): ",
        true,
        input -> {
          try {
            worker.setStartDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (WorkerException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (ParseException e) {
            return new Reply(false, "Неверный формат даты. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите дату окончания (dd-MM-yyyy HH:mm:ss): ",
        false,
        input -> {
          try {
            worker.setEndDate(
                LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (WorkerException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (DateTimeParseException e) {
            return new Reply(false, "Неверный формат даты. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите статус " + Arrays.toString(Worker.Status.values()) + ": ",
        false,
        input -> {
          try {
            worker.setStatus(Worker.Status.valueOf(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (WorkerException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (IllegalArgumentException e) {
            return new Reply(false, "Неверный статус. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    output.writeln("Введите личные данные: ");
    Person person = new Person();

    if (inputValue(
        "Введите цвет глаз " + Arrays.toString(Person.Color.values()) + ": ",
        false,
        input -> {
          try {
            person.setEyeColor(Person.Color.valueOf(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (IllegalArgumentException e) {
            return new Reply(false, "Неверный цвет глаз. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите цвет волос " + Arrays.toString(Person.Color.values()) + ": ",
        true,
        input -> {
          try {
            person.setHairColor(Person.Color.valueOf(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (PersonException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (IllegalArgumentException e) {
            return new Reply(false, "Неверный цвет волос. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    output.writeln("Введите местоположение: ");
    Location location = new Location();

    if (inputValue(
        "Введите x: ",
        true,
        input -> {
          try {
            location.setX(Long.parseLong(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (LocationException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (NumberFormatException e) {
            return new Reply(false, "Неверное значение. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите y: ",
        false,
        input -> {
          try {
            location.setY(Long.parseLong(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (NumberFormatException e) {
            return new Reply(false, "Неверное значение. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите z: ",
        true,
        input -> {
          try {
            location.setZ(Double.parseDouble(input));
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (LocationException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          } catch (NumberFormatException e) {
            return new Reply(false, "Неверное значение. ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    if (inputValue(
        "Введите название: ",
        false,
        input -> {
          try {
            location.setName(input);
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (LocationException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    try {
      person.setLocation(location);
    } catch (PersonException e) {
      output.writeln(e.getMessage() + ". ", Reply.Types.SEVERE.getColor());
      output.writeln("Отмена создания нового элемента");
      return null;
    }

    if (inputValue(
        "Введите ID пасспорта: ",
        true,
        input -> {
          try {
            person.setPassportID(input);
            return new Reply(true, "Успешно", Reply.Types.FINEST);
          } catch (PersonException e) {
            return new Reply(false, e.getMessage() + ". ", Reply.Types.SEVERE);
          }
        })) {
      return null;
    }

    try {
      worker.setPerson(person);
    } catch (WorkerException e) {
      output.writeln(e.getMessage() + ". ", Reply.Types.SEVERE.getColor());
      output.writeln("Отмена создания нового элемента");
      return null;
    }

    return worker;
  }
}
