package ru.lab.server.model.domain.entity.entities.worker.person;

import ru.lab.server.model.domain.dto.dtos.LocationDTO;
import ru.lab.server.model.domain.entity.Entity;
import ru.lab.server.model.domain.entity.exceptions.ValidationException;

import java.util.Objects;
import java.util.ResourceBundle;

public final class Location implements Cloneable, Entity {
  public static final long DEFAULT_ID = 0L;
  public static final long DEFAULT_OWNER_ID = 0L;

  private static final String WRONG_ID_EXCEPTION;
  private static final String WRONG_OWNER_ID_EXCEPTION;
  private static final String WRONG_X_EXCEPTION;
  private static final String WRONG_Z_EXCEPTION;
  private static final String WRONG_NAME_EXCEPTION;

  static {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("internal.Location");

    WRONG_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongId");
    WRONG_OWNER_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongOwnerId");
    WRONG_X_EXCEPTION = resourceBundle.getString("exceptions.wrongX");
    WRONG_Z_EXCEPTION = resourceBundle.getString("exceptions.wrongZ");
    WRONG_NAME_EXCEPTION = resourceBundle.getString("exceptions.wrongName");
  }

  private long id;
  private long ownerId;
  private Long x;
  private long y;
  private Double z;
  private String name;

  public Location(long id, long ownerId, Long x, long y, Double z, String name)
      throws ValidationException {
    checkId(id);
    this.id = id;

    checkOwnerId(ownerId);
    this.ownerId = ownerId;

    checkX(x);
    this.x = x;

    this.y = y;

    checkZ(z);
    this.z = z;

    checkName(name);
    this.name = name;
  }

  @Override
  public LocationDTO toDTO() {
    return new LocationDTO(id, ownerId, x, y, z, name);
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

  public Long getX() {
    return x;
  }

  public void setX(Long x) throws ValidationException {
    checkX(x);
    this.x = x;
  }

  private void checkX(Long x) throws ValidationException {
    if (x != null) {
      return;
    }

    throw new ValidationException(WRONG_X_EXCEPTION);
  }

  public long getY() {
    return y;
  }

  public void setY(long y) {
    this.y = y;
  }

  public Double getZ() {
    return z;
  }

  public void setZ(Double z) throws ValidationException {
    checkZ(z);
    this.z = z;
  }

  private void checkZ(Double z) throws ValidationException {
    if (z != null) {
      return;
    }

    throw new ValidationException(WRONG_Z_EXCEPTION);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) throws ValidationException {
    checkName(name);
    this.name = name;
  }

  private void checkName(String name) throws ValidationException {
    if (name != null && name.length() <= 461) {
      return;
    }

    throw new ValidationException(WRONG_NAME_EXCEPTION);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return id == location.id
        && ownerId == location.ownerId
        && y == location.y
        && Objects.equals(x, location.x)
        && Objects.equals(z, location.z)
        && Objects.equals(name, location.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ownerId, x, y, z, name);
  }

  @Override
  public Location clone() {
    try {
      return new Location(id, ownerId, x, y, z, name);
    } catch (ValidationException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return "Location{"
        + "id="
        + id
        + ", ownerId="
        + ownerId
        + ", x="
        + x
        + ", y="
        + y
        + ", z="
        + z
        + ", name='"
        + name
        + '\''
        + '}';
  }
}
