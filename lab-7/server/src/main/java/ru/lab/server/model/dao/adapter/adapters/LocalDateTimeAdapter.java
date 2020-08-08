package ru.lab.server.model.dao.adapter.adapters;

import ru.lab.server.model.dao.adapter.Adapter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class LocalDateTimeAdapter implements Adapter<LocalDateTime, Timestamp> {
  @Override
  public Timestamp to(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }

    return Timestamp.valueOf(localDateTime);
  }

  @Override
  public LocalDateTime from(Timestamp timestamp) {
    if (timestamp == null) {
      return null;
    }

    return timestamp.toLocalDateTime();
  }
}
