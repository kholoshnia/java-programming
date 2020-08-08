package ru.lab.server.model.dao.adapter.adapters;

import ru.lab.server.model.dao.adapter.Adapter;
import ru.lab.server.model.dao.adapter.exceptions.AdapterException;
import ru.lab.server.model.domain.entity.entities.worker.person.EyeColor;
import ru.lab.server.model.domain.entity.exceptions.ValidationException;

public final class EyeColorAdapter implements Adapter<EyeColor, String> {
  @Override
  public String to(EyeColor eyeColor) {
    if (eyeColor == null) {
      return null;
    }

    return eyeColor.name();
  }

  @Override
  public EyeColor from(String string) throws AdapterException {
    if (string == null) {
      return null;
    }

    EyeColor eyeColor;

    try {
      eyeColor = EyeColor.getEyeColor(string);
    } catch (ValidationException e) {
      throw new AdapterException(e);
    }

    return eyeColor;
  }
}
