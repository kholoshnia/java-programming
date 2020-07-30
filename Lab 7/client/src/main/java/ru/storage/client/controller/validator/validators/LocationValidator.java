package ru.storage.client.controller.validator.validators;

import ru.storage.client.controller.localeManager.LocaleListener;
import ru.storage.client.controller.validator.exceptions.ValidationException;

import java.util.Locale;
import java.util.ResourceBundle;

public final class LocationValidator implements LocaleListener {
  private String wrongXException;
  private String wrongYException;
  private String wrongZException;
  private String wrongNameException;

  @Override
  public void changeLocale(Locale locale) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.LocationValidator");

    wrongXException = resourceBundle.getString("exceptions.wrongX");
    wrongYException = resourceBundle.getString("exceptions.wrongY");
    wrongZException = resourceBundle.getString("exceptions.wrongZ");
    wrongNameException = resourceBundle.getString("exceptions.wrongName");
  }

  public void checkX(String xString) throws ValidationException {
    if (xString == null || xString.isEmpty()) {
      throw new ValidationException(wrongXException);
    }

    try {
      Double.parseDouble(xString);
    } catch (NumberFormatException e) {
      throw new ValidationException(wrongXException, e);
    }
  }

  public void checkY(String yString) throws ValidationException {
    if (yString == null || yString.isEmpty()) {
      throw new ValidationException(wrongYException);
    }

    try {
      Double.parseDouble(yString);
    } catch (NumberFormatException e) {
      throw new ValidationException(wrongYException, e);
    }
  }

  public void checkZ(String zString) throws ValidationException {
    if (zString == null || zString.isEmpty()) {
      throw new ValidationException(wrongZException);
    }

    try {
      Double.parseDouble(zString);
    } catch (NumberFormatException e) {
      throw new ValidationException(wrongZException, e);
    }
  }

  public void checkName(String nameString) throws ValidationException {
    if (nameString == null || nameString.isEmpty()) {
      return;
    }

    if (nameString.length() > 461) {
      throw new ValidationException(wrongNameException);
    }
  }
}
