package ru.lab.server.model.dao.adapter.adapters;

import ru.lab.server.model.dao.adapter.Adapter;

import java.sql.Date;
import java.time.LocalDate;

public final class LocalDateAdapter implements Adapter<LocalDate, Date> {
  @Override
  public Date to(LocalDate localDate) {
    if (localDate == null) {
      return null;
    }

    return Date.valueOf(localDate);
  }

  @Override
  public LocalDate from(Date date) {
    if (date == null) {
      return null;
    }

    return date.toLocalDate();
  }
}
