package ru.storage.server.controller.services.format.hairColor;

import ru.storage.server.model.domain.entity.entities.worker.person.HairColor;

import java.util.Locale;

public abstract class HairColorFormat {
  /**
   * Returns a new instance of the {@link HairColorFormatter}.
   *
   * @param locale formatter locale
   * @return new hair color format instance
   */
  public static HairColorFormat getHairColorInstance(Locale locale) {
    return new HairColorFormatter(locale);
  }

  /**
   * Formats {@link HairColor} using specified {@link Locale}.
   *
   * @param hairColor hair color to format
   * @return localized hair color string
   */
  public abstract String format(HairColor hairColor);
}
