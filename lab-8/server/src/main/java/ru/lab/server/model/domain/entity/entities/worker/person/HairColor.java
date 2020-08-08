package ru.lab.server.model.domain.entity.entities.worker.person;

import ru.lab.server.model.domain.entity.exceptions.ValidationException;

public enum HairColor {
  BLACK,
  BLUE,
  ORANGE,
  WHITE;

  public static HairColor getHairColor(String hairColorString) throws ValidationException {
    if (hairColorString == null) {
      return null;
    }

    HairColor[] hairColors = values();

    for (HairColor hairColor : hairColors) {
      if (hairColor.name().equals(hairColorString)) {
        return hairColor;
      }
    }

    throw new ValidationException();
  }
}
