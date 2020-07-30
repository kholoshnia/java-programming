package ru.storage.server.model.domain.dto.dtos;

import ru.storage.server.model.domain.dto.DTO;
import ru.storage.server.model.domain.entity.entities.worker.person.EyeColor;
import ru.storage.server.model.domain.entity.entities.worker.person.HairColor;
import ru.storage.server.model.domain.entity.entities.worker.person.Location;
import ru.storage.server.model.domain.entity.entities.worker.person.Person;
import ru.storage.server.model.domain.entity.exceptions.ValidationException;

public final class PersonDTO implements DTO<Person> {
  public static final String TABLE_NAME = "persons";

  public static final String ID_COLUMN = "id";
  public static final String OWNER_ID_COLUMN = "owner_id";
  public static final String PASSPORT_ID_COLUMN = "passport_id";
  public static final String EYE_COLOR_COLUMN = "eye_color";
  public static final String HAIR_COLOR_COLUMN = "hair_color";
  public static final String LOCATION_COLUMN = "location";

  public final long id;
  public final long ownerId;
  public final String passportId;
  public final EyeColor eyeColor;
  public final HairColor hairColor;
  public final LocationDTO locationDTO;

  public PersonDTO(
      long id,
      long ownerId,
      String passportId,
      EyeColor eyeColor,
      HairColor hairColor,
      LocationDTO locationDTO) {
    this.id = id;
    this.ownerId = ownerId;
    this.passportId = passportId;
    this.eyeColor = eyeColor;
    this.hairColor = hairColor;
    this.locationDTO = locationDTO;
  }

  @Override
  public Person toEntity() throws ValidationException {
    Location location;

    if (locationDTO != null) {
      location = locationDTO.toEntity();
    } else {
      location = null;
    }

    return new Person(id, ownerId, passportId, eyeColor, hairColor, location);
  }

  @Override
  public String toString() {
    return "PersonDTO{"
        + "id="
        + id
        + ", ownerId="
        + ownerId
        + ", passportId='"
        + passportId
        + '\''
        + ", eyeColor="
        + eyeColor
        + ", hairColor="
        + hairColor
        + ", locationDTO="
        + locationDTO
        + '}';
  }
}
