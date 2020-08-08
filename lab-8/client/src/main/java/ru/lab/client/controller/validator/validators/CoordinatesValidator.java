package ru.lab.client.controller.validator.validators;

import ru.lab.client.controller.localeManager.LocaleListener;
import ru.lab.client.controller.validator.exceptions.ValidationException;

import java.util.Locale;
import java.util.ResourceBundle;

public final class CoordinatesValidator implements LocaleListener {
  private String wrongXException;
  private String wrongYException;

  @Override
  public void changeLocale(Locale locale) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.CoordinatesValidator");

    wrongXException = resourceBundle.getString("exceptions.wrongX");
    wrongYException = resourceBundle.getString("exceptions.wrongY");
  }

  public void checkX(String xString) throws ValidationException {
    if (xString == null || xString.isEmpty()) {
      throw new ValidationException(wrongXException);
    }

    double x;

    try {
      x = Double.parseDouble(xString);
    } catch (NumberFormatException e) {
      throw new ValidationException(wrongXException, e);
    }

    if (x <= -433) {
      throw new ValidationException(wrongXException);
    }
  }

  public void checkY(String yString) throws ValidationException {
    if (yString == null || yString.isEmpty()) {
      throw new ValidationException(wrongYException);
    }

    double y;

    try {
      y = Double.parseDouble(yString);
    } catch (NumberFormatException e) {
      throw new ValidationException(wrongYException, e);
    }

    if (y <= -501.0) {
      throw new ValidationException(wrongYException);
    }
  }
}
