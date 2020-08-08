package ru.lab.server.controller.services.format.hairColor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.server.model.domain.entity.entities.worker.person.HairColor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class HairColorFormatter extends HairColorFormat {
  private static final Logger logger = LogManager.getLogger(HairColorFormatter.class);

  private final Map<HairColor, String> hairColorMap;

  public HairColorFormatter(Locale locale) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.HairColorFormat", locale);
    hairColorMap = initHairColorMap(resourceBundle);
    logger.debug(() -> "Hair color localized map was created.");
  }

  private Map<HairColor, String> initHairColorMap(ResourceBundle resourceBundle) {
    return new HashMap<HairColor, String>() {
      {
        put(HairColor.BLACK, resourceBundle.getString("values.black"));
        put(HairColor.BLUE, resourceBundle.getString("values.blue"));
        put(HairColor.ORANGE, resourceBundle.getString("values.orange"));
        put(HairColor.WHITE, resourceBundle.getString("values.white"));
      }
    };
  }

  @Override
  public String format(HairColor hairColor) {
    if (hairColor == null) {
      return null;
    }

    String result = hairColorMap.get(hairColor);

    logger.info("Got localized status: {}.", () -> result);
    return result;
  }
}
