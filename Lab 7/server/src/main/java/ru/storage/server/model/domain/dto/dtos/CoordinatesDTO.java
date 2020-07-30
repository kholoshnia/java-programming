package ru.storage.server.model.domain.dto.dtos;

import ru.storage.server.model.domain.dto.DTO;
import ru.storage.server.model.domain.entity.entities.worker.Coordinates;
import ru.storage.server.model.domain.entity.exceptions.ValidationException;

public final class CoordinatesDTO implements DTO<Coordinates> {
  public static final String TABLE_NAME = "coordinates";

  public static final String ID_COLUMN = "id";
  public static final String OWNER_ID_COLUMN = "owner_id";
  public static final String X_COLUMN = "x";
  public static final String Y_COLUMN = "y";

  public final long id;
  public final long ownerId;
  public final double x;
  public final Double y;

  public CoordinatesDTO(long id, long ownerId, double x, Double y) {
    this.id = id;
    this.ownerId = ownerId;
    this.x = x;
    this.y = y;
  }

  @Override
  public Coordinates toEntity() throws ValidationException {
    return new Coordinates(id, ownerId, x, y);
  }

  @Override
  public String toString() {
    return "CoordinatesDTO{" + "id=" + id + ", ownerId=" + ownerId + ", x=" + x + ", y=" + y + '}';
  }
}
