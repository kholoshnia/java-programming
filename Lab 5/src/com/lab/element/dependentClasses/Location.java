package com.lab.element.dependentClasses;

/** Coordinates class for worker class */
public class Location {
  private long x; // Поле не может быть null
  private long y;
  private double z; // Поле не может быть null
  private String name; // Длина строки не должна быть больше 461, Поле может быть null

  public long getX() {
    return x;
  }

  public void setX(long x) {
    this.x = x;
  }

  public long getY() {
    return y;
  }

  public void setY(long y) {
    this.y = y;
  }

  public double getZ() {
    return z;
  }

  public void setZ(double z) {
    this.z = z;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) throws LocationException {
    if (name.length() > 461) {
      throw new LocationException("Длина строки не должна быть больше 461");
    } else {
      this.name = name;
    }
  }

  @Override
  public String toString() {
    return "Location { " + "x: " + x + ", y: " + y + ", z: " + z + ", name: '" + name + '\'' + " }";
  }
}
