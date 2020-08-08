package ru.lab.element.dependentClasses;

/** Person class for Worker class */
public class Person {
  private Color eyeColor; // Поле может быть null
  private Color hairColor; // Поле не может быть null
  private Location location; // Поле не может быть null
  private String
      passportID; // Длина строки не должна быть больше 44, Длина строки должна быть не меньше 10,
  // Строка не может быть пустой, Поле не может быть null

  public Color getEyeColor() {
    return eyeColor;
  }

  public void setEyeColor(Color eyeColor) {
    this.eyeColor = eyeColor;
  }

  public Color getHairColor() {
    return hairColor;
  }

  public void setHairColor(Color hairColor) {
    this.hairColor = hairColor;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getPassportID() {
    return passportID;
  }

  public void setPassportID(String passportID) throws PersonException {
    if (passportID.length() > 44) {
      throw new PersonException("Неверный ID паспорта (больше 44)");
    } else if (passportID.length() < 10) {
      throw new PersonException("Неверный ID паспорта (меньше 10)");
    } else {
      this.passportID = passportID;
    }
  }

  @Override
  public String toString() {
    return "Person { "
        + "eyeColor: "
        + eyeColor
        + ", hairColor: "
        + hairColor
        + ", location: "
        + location
        + ", passportID: '"
        + passportID
        + '\''
        + " }";
  }

  public enum Color {
    BLUE,
    WHITE,
    BROWN,
    BLACK,
    YELLOW,
    ORANGE
  }
}
