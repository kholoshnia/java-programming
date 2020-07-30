package ru.storage.server.controller.services.format.date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public final class DateFormatter extends DateFormat {
  private static final Logger logger = LogManager.getLogger(DateFormatter.class);

  private final java.text.DateFormat dateFormat;
  private final DateTimeFormatter dateTimeFormatter;

  public DateFormatter(Locale locale) {
    dateTimeFormatter =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
            .withLocale(locale)
            .withZone(ZoneId.systemDefault());
    dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL, locale);
  }

  @Override
  public String format(ZonedDateTime zonedDateTime) {
    if (zonedDateTime == null) {
      return null;
    }

    String result = dateTimeFormatter.format(zonedDateTime);

    logger.info("Got localized zoned date time: {}.", () -> result);
    return result;
  }

  @Override
  public String format(Date date) {
    if (date == null) {
      return null;
    }

    String result = dateFormat.format(date);

    logger.info("Got localized date: {}.", () -> result);
    return result;
  }

  @Override
  public String format(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }

    String result = dateTimeFormatter.format(localDateTime);

    logger.info("Got localized local date time: {}.", () -> result);
    return result;
  }
}
