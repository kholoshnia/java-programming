package ru.lab.common.generators;

import ru.lab.common.element.Worker;
import ru.lab.common.element.WorkerException;
import com.lab.common.element.dependencies.*;
import ru.lab.common.element.dependencies.*;
import ru.lab.common.tools.Random;
import ru.lab.common.tools.RandomException;

/** Randomizer class generates random element */
public class Randomizer {
  Random random;
  Worker worker;

  /** Default Randomizer constructor */
  public Randomizer() {
    this.random = new Random();
    this.worker = new Worker();
  }

  /**
   * Returns random worker
   *
   * @return Random worker
   * @throws RandomizerException if the randomly generated number is incorrect
   */
  public Worker random() throws RandomizerException {
    try {
      worker.setName(random.randomName() + ' ' + random.randomName() + ' ' + random.randomName());
    } catch (WorkerException e) {
      throw new RandomizerException("Случайное имя сгенерированно неверно", e);
    }

    try {
      worker.setStatus(random.randomEnum(Worker.Status.class));
    } catch (WorkerException e) {
      throw new RandomizerException("Случайный статус сгенерирован неверно", e);
    }

    Person person = new Person();

    person.setEyeColor(random.randomEnum(Person.Color.class));

    try {
      person.setHairColor(random.randomEnum(Person.Color.class));
    } catch (PersonException e) {
      throw new RandomizerException("Случайный цвет волос сгенерирован неверно", e);
    }

    Location location = new Location();

    try {
      location.setX(random.randomInt(-20, 20));
    } catch (RandomException e) {
      throw new RandomizerException("Ошибка генерации случайного значения", e);
    } catch (LocationException e) {
      throw new RandomizerException("Случайная координата x сгенерирована неверно", e);
    }

    try {
      location.setY(random.randomInt(-20, 20));
    } catch (RandomException e) {
      throw new RandomizerException("Случайная координата y сгенерирована неверно", e);
    }

    try {
      location.setZ(random.randomDouble(-20.0, 20.0));
    } catch (RandomException e) {
      throw new RandomizerException("Ошибка генерации случайного значения", e);
    } catch (LocationException e) {
      throw new RandomizerException("Случайная координата z сгенерирована неверно", e);
    }

    try {
      location.setName(random.randomName());
    } catch (LocationException e) {
      throw new RandomizerException("Случайное название сгенерированно неверно", e);
    }

    try {
      person.setLocation(location);
    } catch (PersonException e) {
      throw new RandomizerException("Случайное местоположение сгенерированно неверно", e);
    }

    try {
      person.setPassportID(random.randomInt(1000, 9999) + " " + random.randomInt(100000, 999999));
    } catch (RandomException e) {
      throw new RandomizerException("Ошибка генерации случайного значения", e);
    } catch (PersonException e) {
      throw new RandomizerException("Случайный ID пасспорта сгенерирован неверно", e);
    }

    try {
      worker.setPerson(person);
    } catch (WorkerException e) {
      throw new RandomizerException("Случайная персона сгенерирована неверно", e);
    }

    try {
      worker.setSalary(random.randomDouble(200, 1000000));
    } catch (RandomException e) {
      throw new RandomizerException("Ошибка генерации случайного значения", e);
    } catch (WorkerException e) {
      throw new RandomizerException("Случайная зарплата сгенерирован неверно", e);
    }

    try {
      worker.setStartDate(random.randomDate(2000, 2020));
    } catch (RandomException e) {
      throw new RandomizerException("Ошибка генерации случайного значения", e);
    } catch (WorkerException e) {
      throw new RandomizerException("Случайная начальная дата сгенерирована неверно", e);
    }

    try {
      worker.setEndDate(random.randomDateTime(2020, 2050));
    } catch (RandomException e) {
      throw new RandomizerException("Ошибка генерации случайного значения", e);
    } catch (WorkerException e) {
      throw new RandomizerException("Случайная конечная дата сгенерирована неверно", e);
    }

    Coordinates coordinates = new Coordinates();

    try {
      coordinates.setX(random.randomDouble(-20, 20));
    } catch (RandomException e) {
      throw new RandomizerException("Ошибка генерации случайного значения", e);
    } catch (CoordinatesException e) {
      throw new RandomizerException("Случайная координата x сгенерирована неверно", e);
    }

    try {
      coordinates.setY(random.randomDouble(-20, 20));
    } catch (RandomException e) {
      throw new RandomizerException("Ошибка генерации случайного значения", e);
    } catch (CoordinatesException e) {
      throw new RandomizerException("Случайная координата y сгенерирована неверно", e);
    }

    try {
      worker.setCoordinates(coordinates);
    } catch (WorkerException e) {
      throw new RandomizerException("Случайная координата сгенерирована неверно", e);
    }

    return worker;
  }
}
