package ru.lab.server.controller.services.format.date;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

public abstract class DateFormat {
  /**
   * Returns a new instance of the {@link DateFormatter}.
   *
   * @param locale formatter locale
   * @return new date format instance
   */
  public static DateFormat getDateInstance(Locale locale) {
    return new DateFormatter(locale);
  }

  /**
   * Formats {@link ZonedDateTime} using specified {@link Locale}.
   *
   * @param zonedDateTime local date time to format
   * @return localized status string
   */
  public abstract String format(ZonedDateTime zonedDateTime);

  /**
   * Formats {@link Date} using specified {@link Locale}.
   *
   * @param date date to format
   * @return localized status string
   */
  public abstract String format(Date date);

  /**
   * Formats {@link LocalDateTime} using specified {@link Locale}.
   *
   * @param localDateTime local date time to format
   * @return localized status string
   */
  public abstract String format(LocalDateTime localDateTime);
}
