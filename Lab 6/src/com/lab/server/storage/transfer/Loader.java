package com.lab.server.storage.transfer;

import com.lab.common.element.Worker;
import com.lab.common.element.WorkerException;
import com.lab.common.element.dependencies.*;
import com.lab.common.io.Input;
import com.lab.common.io.InputException;
import com.lab.common.tools.Files;
import com.lab.common.tools.FilesException;
import com.lab.server.storage.collection.Collection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/** z Loader class */
public class Loader { // TODO: JSON, CSV, PostgreSQL
  /**
   * Loads collection from the file
   *
   * @return Reply about load correctness
   */
  public Collection load(String dataFilePath) throws LoaderException {
    Collection collection = new Collection();
    Input in;
    String input;
    try {
      new Files().checkFile(dataFilePath);
      in = new Input(new BufferedReader(new InputStreamReader(new FileInputStream(dataFilePath))));
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
        collection.put(key, worker);
      }
    } catch (FilesException e) {
      throw new LoaderException("Ошибка при проверке файла", e);
    } catch (FileNotFoundException e) {
      throw new LoaderException("Файл не найден", e);
    } catch (NullPointerException e) {
      throw new LoaderException("Неожиданный конец файла", e);
    } catch (ParseException
        | IllegalArgumentException
        | CoordinatesException
        | PersonException
        | LocationException
        | WorkerException
        | InputException e) {
      throw new LoaderException("Неверный формат входных данных", e);
    }
    return collection;
  }
}
