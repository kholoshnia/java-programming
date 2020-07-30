package ru.storage.server.model.dao.adapter.adapters;

import ru.storage.server.model.dao.adapter.Adapter;
import ru.storage.server.model.dao.adapter.exceptions.AdapterException;
import ru.storage.server.model.domain.entity.entities.worker.person.HairColor;
import ru.storage.server.model.domain.entity.exceptions.ValidationException;

public final class HairColorAdapter implements Adapter<HairColor, String> {
  @Override
  public String to(HairColor hairColor) {
    if (hairColor == null) {
      return null;
    }

    return hairColor.name();
  }

  @Override
  public HairColor from(String string) throws AdapterException {
    if (string == null) {
      return null;
    }

    HairColor hairColor;

    try {
      hairColor = HairColor.getHairColor(string);
    } catch (ValidationException e) {
      throw new AdapterException(e);
    }

    return hairColor;
  }
}
