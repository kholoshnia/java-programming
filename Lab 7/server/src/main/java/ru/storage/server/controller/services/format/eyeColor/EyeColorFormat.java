package ru.storage.server.controller.services.format.eyeColor;

import ru.storage.server.model.domain.entity.entities.worker.person.EyeColor;

import java.util.Locale;

public abstract class EyeColorFormat {
  /**
   * Returns a new instance of the {@link EyeColorFormatter}.
   *
   * @param locale formatter locale
   * @return new eye color format instance
   */
  public static EyeColorFormat getEyeColorInstance(Locale locale) {
    return new EyeColorFormatter(locale);
  }

  /**
   * Formats {@link EyeColor} using specified {@link Locale}.
   *
   * @param eyeColor eye color to format
   * @return localized eye color string
   */
  public abstract String format(EyeColor eyeColor);
}
