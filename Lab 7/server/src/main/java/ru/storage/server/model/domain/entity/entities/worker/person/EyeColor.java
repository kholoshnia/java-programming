package ru.storage.server.model.domain.entity.entities.worker.person;

import ru.storage.server.model.domain.entity.exceptions.ValidationException;

public enum EyeColor {
  BLACK,
  YELLOW,
  ORANGE,
  WHITE,
  BROWN;

  public static EyeColor getEyeColor(String eyeColorString) throws ValidationException {
    if (eyeColorString == null) {
      return null;
    }

    EyeColor[] eyeColors = values();

    for (EyeColor eyeColor : eyeColors) {
      if (eyeColor.name().equals(eyeColorString)) {
        return eyeColor;
      }
    }

    throw new ValidationException();
  }
}
