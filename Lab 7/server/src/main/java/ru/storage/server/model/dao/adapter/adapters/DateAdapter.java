package ru.storage.server.model.dao.adapter.adapters;

import ru.storage.server.model.dao.adapter.Adapter;

import java.sql.Timestamp;
import java.util.Date;

public final class DateAdapter implements Adapter<Date, Timestamp> {
  @Override
  public Timestamp to(Date date) {
    if (date == null) {
      return null;
    }

    return new Timestamp(date.getTime());
  }

  @Override
  public Date from(Timestamp timestamp) {
    if (timestamp == null) {
      return null;
    }

    return new java.util.Date(timestamp.getTime());
  }
}
