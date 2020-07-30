package ru.storage.server.model.domain.dto.dtos;

import ru.storage.server.model.domain.dto.DTO;
import ru.storage.server.model.domain.entity.entities.worker.person.Location;
import ru.storage.server.model.domain.entity.exceptions.ValidationException;

public final class LocationDTO implements DTO<Location> {
  public static final String TABLE_NAME = "locations";

  public static final String ID_COLUMN = "id";
  public static final String OWNER_ID_COLUMN = "owner_id";
  public static final String X_COLUMN = "x";
  public static final String Y_COLUMN = "y";
  public static final String Z_COLUMN = "z";
  public static final String NAME_COLUMN = "name";

  public final long id;
  public final long ownerId;
  public final Long x;
  public final long y;
  public final Double z;
  public final String name;

  public LocationDTO(long id, long ownerId, Long x, long y, Double z, String name) {
    this.id = id;
    this.ownerId = ownerId;
    this.x = x;
    this.y = y;
    this.z = z;
    this.name = name;
  }

  @Override
  public Location toEntity() throws ValidationException {
    return new Location(id, ownerId, x, y, z, name);
  }

  @Override
  public String toString() {
    return "LocationDTO{"
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
