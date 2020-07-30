package ru.storage.server.controller.services.format.eyeColor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.storage.server.model.domain.entity.entities.worker.person.EyeColor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class EyeColorFormatter extends EyeColorFormat {
  private static final Logger logger = LogManager.getLogger(EyeColorFormatter.class);

  private final Map<EyeColor, String> eyeColorMap;

  public EyeColorFormatter(Locale locale) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.EyeColorFormat", locale);
    eyeColorMap = initEyeColorMap(resourceBundle);
    logger.debug(() -> "Eye color localized map was created.");
  }

  private Map<EyeColor, String> initEyeColorMap(ResourceBundle resourceBundle) {
    return new HashMap<EyeColor, String>() {
      {
        put(EyeColor.BLACK, resourceBundle.getString("values.black"));
        put(EyeColor.YELLOW, resourceBundle.getString("values.yellow"));
        put(EyeColor.ORANGE, resourceBundle.getString("values.orange"));
        put(EyeColor.WHITE, resourceBundle.getString("values.white"));
        put(EyeColor.BROWN, resourceBundle.getString("values.brown"));
      }
    };
  }

  @Override
  public String format(EyeColor eyeColor) {
    if (eyeColor == null) {
      return null;
    }

    String result = eyeColorMap.get(eyeColor);

    logger.info("Got localized status: {}.", () -> result);
    return result;
  }
}
