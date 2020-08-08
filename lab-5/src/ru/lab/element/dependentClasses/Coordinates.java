package ru.lab.element.dependentClasses;

/** Coordinates class for Worker class */
public class Coordinates {
  private double x; // Значение поля должно быть больше -433
  private double y; // Значение поля должно быть больше -501, Поле не может быть null

  public double getX() {
    return x;
  }

  public void setX(double x) throws CoordinatesException {
    if (x <= -433) {
      throw new CoordinatesException("Значение x должно быть больше -433");
    } else {
      this.x = x;
    }
  }

  public double getY() {
    return y;
  }

  public void setY(double y) throws CoordinatesException {
    if (y <= -501) {
      throw new CoordinatesException("Значение y должно быть больше -501");
    } else {
      this.y = y;
    }
  }

  @Override
  public String toString() {
    return "Coordinates { " + "x: " + x + ", y: " + y + " }";
  }
}
