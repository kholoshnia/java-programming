package ru.storage.server.model.domain.entity.entities.worker;

import ru.storage.server.model.domain.dto.dtos.CoordinatesDTO;
import ru.storage.server.model.domain.entity.Entity;
import ru.storage.server.model.domain.entity.exceptions.ValidationException;

import java.util.Objects;
import java.util.ResourceBundle;

public final class Coordinates implements Cloneable, Entity {
  public static final long DEFAULT_ID = 0L;
  public static final long DEFAULT_OWNER_ID = 0L;

  private static final String WRONG_ID_EXCEPTION;
  private static final String WRONG_OWNER_ID_EXCEPTION;
  private static final String WRONG_X_EXCEPTION;
  private static final String WRONG_Y_EXCEPTION;

  static {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("internal.Coordinates");

    WRONG_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongId");
    WRONG_OWNER_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongOwnerId");
    WRONG_X_EXCEPTION = resourceBundle.getString("exceptions.wrongX");
    WRONG_Y_EXCEPTION = resourceBundle.getString("exceptions.wrongY");
  }

  private long id;
  private long ownerId;
  private double x;
  private Double y;

  public Coordinates(long id, long ownerId, double x, Double y) throws ValidationException {
    checkId(id);
    this.id = id;

    checkOwnerId(ownerId);
    this.ownerId = ownerId;

    checkX(x);
    this.x = x;

    checkY(y);
    this.y = y;
  }

  @Override
  public CoordinatesDTO toDTO() {
    return new CoordinatesDTO(id, ownerId, x, y);
  }

  public final long getId() {
    return id;
  }

  public final void setId(long id) throws ValidationException {
    checkId(id);
    this.id = id;
  }

  private void checkId(long id) throws ValidationException {
    if (id > 0 || id == DEFAULT_ID) {
      return;
    }

    throw new ValidationException(WRONG_ID_EXCEPTION);
  }

  public final long getOwnerId() {
    return ownerId;
  }

  public final void setOwnerId(long ownerId) throws ValidationException {
    checkOwnerId(ownerId);
    this.ownerId = ownerId;
  }

  private void checkOwnerId(long ownerId) throws ValidationException {
    if (ownerId > 0 || ownerId == DEFAULT_OWNER_ID) {
      return;
    }

    throw new ValidationException(WRONG_OWNER_ID_EXCEPTION);
  }

  public double getX() {
    return x;
  }

  public void setX(double x) throws ValidationException {
    checkX(x);
    this.x = x;
  }

  private void checkX(double x) throws ValidationException {
    if (x > -433.0) {
      return;
    }

    throw new ValidationException(WRONG_X_EXCEPTION);
  }

  public Double getY() {
    return y;
  }

  public void setY(Double y) throws ValidationException {
    checkY(y);
    this.y = y;
  }

  private void checkY(Double y) throws ValidationException {
    if (y != null && y > -501.0) {
      return;
    }

    throw new ValidationException(WRONG_Y_EXCEPTION);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordinates that = (Coordinates) o;
    return id == that.id
        && ownerId == that.ownerId
        && Double.compare(that.x, x) == 0
        && Objects.equals(y, that.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ownerId, x, y);
  }

  @Override
  public Coordinates clone() {
    try {
      return new Coordinates(id, ownerId, x, y);
    } catch (ValidationException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return "Coordinates{" + "id=" + id + ", ownerId=" + ownerId + ", x=" + x + ", y=" + y + '}';
  }
}
