package ru.storage.client.controller.validator.validators;

import ru.storage.client.controller.localeManager.LocaleListener;
import ru.storage.client.controller.validator.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class PersonValidator implements LocaleListener {
  private String wrongPassportIdException;
  private String wrongEyeColorException;
  private String wrongHairColorException;

  private List<String> eyeColors;
  private List<String> hairColors;

  @Override
  public void changeLocale(Locale locale) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.PersonValidator");

    eyeColors =
        new ArrayList<String>() {
          {
            add(resourceBundle.getString("eyeColors.black"));
            add(resourceBundle.getString("eyeColors.yellow"));
            add(resourceBundle.getString("eyeColors.orange"));
            add(resourceBundle.getString("eyeColors.white"));
            add(resourceBundle.getString("eyeColors.brown"));
          }
        };

    hairColors =
        new ArrayList<String>() {
          {
            add(resourceBundle.getString("hairColors.black"));
            add(resourceBundle.getString("hairColors.blue"));
            add(resourceBundle.getString("hairColors.orange"));
            add(resourceBundle.getString("hairColors.white"));
          }
        };

    wrongPassportIdException = resourceBundle.getString("exceptions.wrongPassportId");
    wrongEyeColorException =
        String.format("%s %s", resourceBundle.getString("exceptions.wrongEyeColor"), eyeColors);
    wrongHairColorException =
        String.format("%s %s", resourceBundle.getString("exceptions.wrongHairColor"), hairColors);
  }

  public void checkPassportId(String passportIdString) throws ValidationException {
    if (passportIdString == null
        || passportIdString.length() < 10
        || passportIdString.length() > 40) {
      throw new ValidationException(wrongPassportIdException);
    }
  }

  public void checkEyeColor(String eyeColorString) throws ValidationException {
    if (eyeColorString == null || eyeColorString.isEmpty()) {
      return;
    }

    if (!eyeColors.contains(eyeColorString)) {
      throw new ValidationException(wrongEyeColorException);
    }
  }

  public void checkHairColor(String hairColorString) throws ValidationException {
    if (hairColorString == null || hairColorString.isEmpty()) {
      throw new ValidationException(wrongHairColorException);
    }

    if (!hairColors.contains(hairColorString)) {
      throw new ValidationException(wrongHairColorException);
    }
  }
}
