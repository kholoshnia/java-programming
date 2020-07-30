package ru.storage.server.model.domain.entity.entities.worker.person;

import ru.storage.server.model.domain.dto.dtos.LocationDTO;
import ru.storage.server.model.domain.dto.dtos.PersonDTO;
import ru.storage.server.model.domain.entity.Entity;
import ru.storage.server.model.domain.entity.exceptions.ValidationException;

import java.util.Objects;
import java.util.ResourceBundle;

public final class Person implements Cloneable, Entity {
  public static final long DEFAULT_ID = 0L;
  public static final long DEFAULT_OWNER_ID = 0L;

  private static final String WRONG_ID_EXCEPTION;
  private static final String WRONG_OWNER_ID_EXCEPTION;
  private static final String WRONG_PASSPORT_ID_EXCEPTION;
  private static final String WRONG_HAIR_COLOR_EXCEPTION;
  private static final String WRONG_LOCATION_EXCEPTION;

  static {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("internal.Person");

    WRONG_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongId");
    WRONG_OWNER_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongOwnerId");
    WRONG_PASSPORT_ID_EXCEPTION = resourceBundle.getString("exceptions.wrongPassportId");
    WRONG_HAIR_COLOR_EXCEPTION = resourceBundle.getString("exceptions.wrongHairColor");
    WRONG_LOCATION_EXCEPTION = resourceBundle.getString("exceptions.wrongLocation");
  }

  private long id;
  private long ownerId;
  private String passportId;
  private EyeColor eyeColor;
  private HairColor hairColor;
  private Location location;

  public Person(
      long id,
      long ownerId,
      String passportId,
      EyeColor eyeColor,
      HairColor hairColor,
      Location location)
      throws ValidationException {
    checkId(id);
    this.id = id;

    checkOwnerId(ownerId);
    this.ownerId = ownerId;

    checkPassportId(passportId);
    this.passportId = passportId;

    this.eyeColor = eyeColor;

    checkHairColor(hairColor);
    this.hairColor = hairColor;

    checkLocation(location);
    this.location = location;
  }

  @Override
  public PersonDTO toDTO() {
    LocationDTO locationDTO;

    if (location != null) {
      locationDTO = location.toDTO();
    } else {
      locationDTO = null;
    }

    return new PersonDTO(id, ownerId, passportId, eyeColor, hairColor, locationDTO);
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

  public String getPassportId() {
    return passportId;
  }

  public void setPassportId(String passportId) throws ValidationException {
    checkPassportId(passportId);
    this.passportId = passportId;
  }

  private void checkPassportId(String passportId) throws ValidationException {
    if (passportId != null && passportId.length() >= 10 && passportId.length() <= 40) {

      return;
    }

    throw new ValidationException(WRONG_PASSPORT_ID_EXCEPTION);
  }

  public EyeColor getEyeColor() {
    return eyeColor;
  }

  public void setEyeColor(EyeColor eyeColor) {
    this.eyeColor = eyeColor;
  }

  public HairColor getHairColor() {
    return hairColor;
  }

  public void setHairColor(HairColor hairColor) throws ValidationException {
    checkHairColor(hairColor);
    this.hairColor = hairColor;
  }

  private void checkHairColor(HairColor hairColor) throws ValidationException {
    if (hairColor != null) {
      return;
    }

    throw new ValidationException(WRONG_HAIR_COLOR_EXCEPTION);
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) throws ValidationException {
    checkLocation(location);
    this.location = location;
  }

  private void checkLocation(Location location) throws ValidationException {
    if (location != null) {
      return;
    }

    throw new ValidationException(WRONG_LOCATION_EXCEPTION);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return id == person.id
        && ownerId == person.ownerId
        && Objects.equals(passportId, person.passportId)
        && eyeColor == person.eyeColor
        && hairColor == person.hairColor
        && Objects.equals(location, person.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ownerId, passportId, eyeColor, hairColor, location);
  }

  @Override
  public Person clone() {
    try {
      return new Person(id, ownerId, passportId, eyeColor, hairColor, location);
    } catch (ValidationException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return "Person{"
        + "id="
        + id
        + ", ownerId="
        + ownerId
        + ", passportID='"
        + passportId
        + '\''
        + ", eyeColor="
        + eyeColor
        + ", hairColor="
        + hairColor
        + ", location="
        + location
        + '}';
  }
}
