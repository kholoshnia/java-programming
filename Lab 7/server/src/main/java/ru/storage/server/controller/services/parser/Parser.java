package ru.storage.server.controller.services.parser;

import com.google.inject.Inject;
import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;
import ru.storage.server.controller.services.parser.exceptions.ParserException;
import ru.storage.server.model.domain.entity.entities.worker.Status;
import ru.storage.server.model.domain.entity.entities.worker.person.EyeColor;
import ru.storage.server.model.domain.entity.entities.worker.person.HairColor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/** The Parser class provides methods for parsing a string into other types. */
public final class Parser {
  private static final Logger logger = LogManager.getLogger(Parser.class);

  private static final String PARSE_INTEGER_EXCEPTION;
  private static final String PARSE_LONG_EXCEPTION;
  private static final String PARSE_DOUBLE_EXCEPTION;
  private static final String PARSE_STATUS_EXCEPTION;
  private static final String PARSE_LOCAL_DATE_TIME_EXCEPTION;

  static {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("internal.Parser");

    PARSE_INTEGER_EXCEPTION = resourceBundle.getString("exceptions.parseInteger");
    PARSE_LONG_EXCEPTION = resourceBundle.getString("exceptions.parseLong");
    PARSE_DOUBLE_EXCEPTION = resourceBundle.getString("exceptions.parseDouble");
    PARSE_STATUS_EXCEPTION = resourceBundle.getString("exceptions.parseStatus");
    PARSE_LOCAL_DATE_TIME_EXCEPTION = resourceBundle.getString("exceptions.parseLocalDateTime");
  }

  private final String dateTimePattern;

  @Inject
  public Parser(Configuration configuration) {
    dateTimePattern = configuration.getString("dateTimePattern");
  }

  public Integer parseInteger(String integerString) throws ParserException {
    if (integerString == null || integerString.isEmpty()) {
      logger.info(() -> "Got null integer.");
      return null;
    }

    int result;

    try {
      result = Integer.parseInt(integerString);
    } catch (NumberFormatException e) {
      logger.info(
          "Exception was caught during parsing integer string: \"{}\".",
          (Supplier<?>) () -> integerString,
          e);
      throw new ParserException(PARSE_INTEGER_EXCEPTION, e);
    }

    return result;
  }

  public Long parseLong(String longString) throws ParserException {
    if (longString == null || longString.isEmpty()) {
      logger.info(() -> "Got null long.");
      return null;
    }

    long result;

    try {
      result = Long.parseLong(longString);
    } catch (NumberFormatException e) {
      logger.info(
          "Exception was caught during parsing long string: \"{}\".",
          (Supplier<?>) () -> longString,
          e);
      throw new ParserException(PARSE_LONG_EXCEPTION, e);
    }

    return result;
  }

  public Double parseDouble(String doubleString) throws ParserException {
    if (doubleString == null || doubleString.isEmpty()) {
      logger.info(() -> "Got null double.");
      return null;
    }

    double result;

    try {
      result = Double.parseDouble(doubleString);
    } catch (NumberFormatException e) {
      logger.info(
          "Exception was caught during parsing double string: \"{}\".",
          (Supplier<?>) () -> doubleString,
          e);
      throw new ParserException(PARSE_DOUBLE_EXCEPTION, e);
    }

    return result;
  }

  public String parseString(String string) {
    if (string == null || string.isEmpty()) {
      return null;
    }

    return string;
  }

  public Status parseStatus(String statusString, Locale locale) throws ParserException {
    if (statusString == null || statusString.isEmpty()) {
      logger.info(() -> "Got null status.");
      return null;
    }

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.StatusFormat", locale);

    Status[] statuses = Status.values();
    Enumeration<String> keys = resourceBundle.getKeys();

    for (Status status : statuses) {
      while (keys.hasMoreElements()) {
        if (resourceBundle.getString(keys.nextElement()).equals(statusString)) {
          return status;
        }
      }
    }

    logger.info("Got wrong status: {}.", () -> statusString);
    throw new ParserException(PARSE_STATUS_EXCEPTION);
  }

  public EyeColor parseEyeColor(String eyeColorString, Locale locale) throws ParserException {
    if (eyeColorString == null || eyeColorString.isEmpty()) {
      logger.info(() -> "Got null eye color.");
      return null;
    }

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.EyeColorFormat", locale);

    EyeColor[] eyeColors = EyeColor.values();
    Enumeration<String> keys = resourceBundle.getKeys();

    for (EyeColor eyeColor : eyeColors) {
      while (keys.hasMoreElements()) {
        if (resourceBundle.getString(keys.nextElement()).equals(eyeColorString)) {
          return eyeColor;
        }
      }
    }

    logger.info("Got wrong status: {}.", () -> eyeColorString);
    throw new ParserException(PARSE_STATUS_EXCEPTION);
  }

  public HairColor parseHairColor(String hairColorString, Locale locale) throws ParserException {
    if (hairColorString == null || hairColorString.isEmpty()) {
      logger.info(() -> "Got null hair color.");
      return null;
    }

    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.HairColorFormat", locale);

    HairColor[] hairColors = HairColor.values();
    Enumeration<String> keys = resourceBundle.getKeys();

    for (HairColor hairColor : hairColors) {
      while (keys.hasMoreElements()) {
        if (resourceBundle.getString(keys.nextElement()).equals(hairColorString)) {
          return hairColor;
        }
      }
    }

    logger.info("Got wrong status: {}.", () -> hairColorString);
    throw new ParserException(PARSE_STATUS_EXCEPTION);
  }

  public Date parseDate(String dateString) throws ParserException {
    if (dateString == null || dateString.isEmpty()) {
      logger.info(() -> "Got null date.");
      return null;
    }

    Date result;

    try {
      result = new SimpleDateFormat(dateTimePattern).parse(dateString);
    } catch (ParseException e) {
      logger.info(
          "Exception was caught during parsing date string: \"{}\".",
          (Supplier<?>) () -> dateString,
          e);
      throw new ParserException(PARSE_LOCAL_DATE_TIME_EXCEPTION, e);
    }

    return result;
  }

  public LocalDateTime parseLocalDateTime(String localDateTimeString) throws ParserException {
    if (localDateTimeString == null || localDateTimeString.isEmpty()) {
      logger.info(() -> "Got null local date time.");
      return null;
    }

    LocalDateTime result;

    try {
      result = LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    } catch (DateTimeParseException e) {
      logger.info(
          "Exception was caught during parsing local date time string: \"{}\".",
          (Supplier<?>) () -> localDateTimeString,
          e);
      throw new ParserException(PARSE_LOCAL_DATE_TIME_EXCEPTION, e);
    }

    return result;
  }
}
