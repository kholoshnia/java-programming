package ru.storage.client.controller.validator.validators;

import com.google.inject.Inject;
import org.apache.commons.configuration2.Configuration;
import ru.storage.client.controller.localeManager.LocaleListener;
import ru.storage.client.controller.validator.exceptions.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class WorkerValidator implements LocaleListener {
  private final String dateTimePattern;

  private String wrongIdException;
  private String wrongKeyException;
  private String wrongNameException;
  private String wrongSalaryException;
  private String wrongStartDateException;
  private String wrongEndDateException;
  private String wrongStatusException;

  private List<String> statuses;

  @Inject
  public WorkerValidator(Configuration configuration) {
    dateTimePattern = configuration.getString("dateTimePattern");
  }

  @Override
  public void changeLocale(Locale locale) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.WorkerValidator");

    statuses =
        new ArrayList<String>() {
          {
            add(resourceBundle.getString("statuses.fired"));
            add(resourceBundle.getString("statuses.hired"));
            add(resourceBundle.getString("statuses.promotion"));
          }
        };

    wrongIdException = resourceBundle.getString("exceptions.wrongId");
    wrongKeyException = resourceBundle.getString("exceptions.wrongKey");
    wrongNameException = resourceBundle.getString("exceptions.wrongName");
    wrongSalaryException = resourceBundle.getString("exceptions.wrongSalary");
    wrongStartDateException = resourceBundle.getString("exceptions.wrongStartDate");
    wrongEndDateException = resourceBundle.getString("exceptions.wrongEndDate");
    wrongStatusException =
        String.format("%s %s", resourceBundle.getString("exceptions.wrongStatus"), statuses);
  }

  public void checkId(String idString) throws ValidationException {
    if (idString == null || idString.isEmpty()) {
      throw new ValidationException(wrongIdException);
    }

    long id;

    try {
      id = Long.parseLong(idString);
    } catch (NumberFormatException e) {
      throw new ValidationException(wrongIdException, e);
    }

    if (id < 0) {
      throw new ValidationException(wrongIdException);
    }
  }

  public void checkKey(String keyString) throws ValidationException {
    if (keyString == null || keyString.isEmpty()) {
      throw new ValidationException(wrongKeyException);
    }

    long key;

    try {
      key = Integer.parseInt(keyString);
    } catch (NumberFormatException e) {
      throw new ValidationException(wrongKeyException, e);
    }

    if (key < 0) {
      throw new ValidationException(wrongKeyException);
    }
  }

  public void checkName(String nameString) throws ValidationException {
    if (nameString == null || nameString.isEmpty()) {
      throw new ValidationException(wrongNameException);
    }
  }

  public void checkSalary(String salaryString) throws ValidationException {
    if (salaryString == null || salaryString.isEmpty()) {
      throw new ValidationException(wrongSalaryException);
    }

    float salary;

    try {
      salary = Float.parseFloat(salaryString);
    } catch (NumberFormatException e) {
      throw new ValidationException(wrongSalaryException, e);
    }

    if (salary <= 0) {
      throw new ValidationException(wrongSalaryException);
    }
  }

  public void checkStartDate(String startDateString) throws ValidationException {
    if (startDateString == null || startDateString.isEmpty()) {
      throw new ValidationException(wrongStartDateException);
    }

    try {
      new SimpleDateFormat(dateTimePattern).parse(startDateString);
    } catch (ParseException e) {
      throw new ValidationException(wrongStartDateException, e);
    }
  }

  public void checkEndDate(String endDateString) throws ValidationException {
    if (endDateString == null || endDateString.isEmpty()) {
      return;
    }

    try {
      LocalDateTime.parse(endDateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    } catch (DateTimeParseException e) {
      throw new ValidationException(wrongEndDateException, e);
    }
  }

  public void checkStatus(String statusString) throws ValidationException {
    if (statusString == null || statusString.isEmpty()) {
      return;
    }

    if (!statuses.contains(statusString)) {
      throw new ValidationException(wrongStatusException);
    }
  }
}
