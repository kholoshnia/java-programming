package ru.lab.element.dependentClasses;

/** Coordinates class exception */
public class CoordinatesException extends Exception {
  public CoordinatesException(String message) {
    super("CoordinatesException: ".concat(message));
  }
}
