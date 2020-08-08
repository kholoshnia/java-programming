package ru.lab.server.model.dao.daos;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.server.model.dao.DAO;
import ru.lab.server.model.dao.adapter.Adapter;
import ru.lab.server.model.dao.adapter.exceptions.AdapterException;
import ru.lab.server.model.dao.exceptions.DAOException;
import ru.lab.server.model.domain.dto.dtos.LocationDTO;
import ru.lab.server.model.domain.dto.dtos.PersonDTO;
import ru.lab.server.model.domain.entity.entities.worker.person.EyeColor;
import ru.lab.server.model.domain.entity.entities.worker.person.HairColor;
import ru.lab.server.model.source.DataSource;
import ru.lab.server.model.source.exceptions.DataSourceException;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PersonDAO implements DAO<Long, PersonDTO> {
  private static final Logger logger = LogManager.getLogger(PersonDAO.class);

  private static final String SELECT_ALL = "SELECT * FROM " + PersonDTO.TABLE_NAME;

  private static final String SELECT_BY_ID = SELECT_ALL + " WHERE " + PersonDTO.ID_COLUMN + " = ?";

  private static final String INSERT =
      "INSERT INTO "
          + PersonDTO.TABLE_NAME
          + " ("
          + PersonDTO.OWNER_ID_COLUMN
          + ", "
          + PersonDTO.PASSPORT_ID_COLUMN
          + ", "
          + PersonDTO.EYE_COLOR_COLUMN
          + ", "
          + PersonDTO.HAIR_COLOR_COLUMN
          + ", "
          + PersonDTO.LOCATION_COLUMN
          + ") VALUES (?, ?, ?, ?, ?)";

  private static final String UPDATE =
      "UPDATE "
          + PersonDTO.TABLE_NAME
          + " SET "
          + PersonDTO.OWNER_ID_COLUMN
          + " = ?, "
          + PersonDTO.PASSPORT_ID_COLUMN
          + " = ?, "
          + PersonDTO.EYE_COLOR_COLUMN
          + " = ?, "
          + PersonDTO.HAIR_COLOR_COLUMN
          + " = ?, "
          + PersonDTO.LOCATION_COLUMN
          + " = ? WHERE "
          + PersonDTO.ID_COLUMN
          + " = ?";

  private static final String DELETE =
      "DELETE FROM " + PersonDTO.TABLE_NAME + " WHERE " + PersonDTO.ID_COLUMN + " = ?";

  private static final String CANNOT_GET_ALL_PERSONS_EXCEPTION;
  private static final String CANNOT_GET_PERSON_BY_ID_EXCEPTION;
  private static final String CANNOT_INSERT_PERSON_EXCEPTION;
  private static final String CANNOT_GET_GENERATED_PERSON_ID_EXCEPTION;
  private static final String CANNOT_UPDATE_PERSON_EXCEPTION;
  private static final String CANNOT_DELETE_PERSON_EXCEPTION;

  static {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("internal.PersonDAO");

    CANNOT_GET_ALL_PERSONS_EXCEPTION = resourceBundle.getString("exceptions.cannotGetAllPersons");
    CANNOT_GET_PERSON_BY_ID_EXCEPTION = resourceBundle.getString("exceptions.cannotGetPersonById");
    CANNOT_INSERT_PERSON_EXCEPTION = resourceBundle.getString("exceptions.cannotInsertPerson");
    CANNOT_GET_GENERATED_PERSON_ID_EXCEPTION =
        resourceBundle.getString("exceptions.cannotGetGeneratedPersonId");
    CANNOT_UPDATE_PERSON_EXCEPTION = resourceBundle.getString("exceptions.cannotUpdatePerson");
    CANNOT_DELETE_PERSON_EXCEPTION = resourceBundle.getString("exceptions.cannotDeletePerson");
  }

  private final DataSource dataSource;
  private final Adapter<EyeColor, String> eyeColorAdapter;
  private final Adapter<HairColor, String> hairColorAdapter;
  private final DAO<Long, LocationDTO> locationDAO;

  @Inject
  public PersonDAO(
      DataSource dataSource,
      Adapter<EyeColor, String> eyeColorAdapter,
      Adapter<HairColor, String> hairColorAdapter,
      DAO<Long, LocationDTO> locationDAO) {
    this.dataSource = dataSource;
    this.eyeColorAdapter = eyeColorAdapter;
    this.hairColorAdapter = hairColorAdapter;
    this.locationDAO = locationDAO;
  }

  @Override
  public List<PersonDTO> getAll() throws DAOException, DataSourceException {
    List<PersonDTO> allPersonDTOs = new ArrayList<>();
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(SELECT_ALL, Statement.NO_GENERATED_KEYS);

    try {
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        long id = resultSet.getLong(PersonDTO.ID_COLUMN);
        long ownerId = resultSet.getLong(PersonDTO.OWNER_ID_COLUMN);
        String passportId = resultSet.getObject(PersonDTO.PASSPORT_ID_COLUMN, String.class);
        EyeColor eyeColor =
            eyeColorAdapter.from(resultSet.getObject(PersonDTO.EYE_COLOR_COLUMN, String.class));
        HairColor hairColor =
            hairColorAdapter.from(resultSet.getObject(PersonDTO.HAIR_COLOR_COLUMN, String.class));

        long locationId = resultSet.getLong(PersonDTO.LOCATION_COLUMN);
        LocationDTO locationDTO = locationDAO.getByKey(locationId);

        PersonDTO personDTO =
            new PersonDTO(id, ownerId, passportId, eyeColor, hairColor, locationDTO);
        allPersonDTOs.add(personDTO);
      }
    } catch (SQLException | AdapterException e) {
      logger.error(() -> "Cannot get all persons.", e);
      throw new DAOException(CANNOT_GET_ALL_PERSONS_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Got all persons.");
    return allPersonDTOs;
  }

  @Override
  public PersonDTO getByKey(@Nonnull Long id) throws DAOException, DataSourceException {
    PersonDTO personDTO = null;
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(SELECT_BY_ID, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        long ownerId = resultSet.getLong(PersonDTO.OWNER_ID_COLUMN);
        String passportId = resultSet.getObject(PersonDTO.PASSPORT_ID_COLUMN, String.class);
        EyeColor eyeColor =
            eyeColorAdapter.from(resultSet.getObject(PersonDTO.EYE_COLOR_COLUMN, String.class));
        HairColor hairColor =
            hairColorAdapter.from(resultSet.getObject(PersonDTO.HAIR_COLOR_COLUMN, String.class));

        long locationId = resultSet.getLong(PersonDTO.LOCATION_COLUMN);
        LocationDTO locationDTO = locationDAO.getByKey(locationId);

        personDTO = new PersonDTO(id, ownerId, passportId, eyeColor, hairColor, locationDTO);
      }
    } catch (SQLException | AdapterException e) {
      logger.error("Cannot get person by id.", e);
      throw new DAOException(CANNOT_GET_PERSON_BY_ID_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Got person by id.");
    return personDTO;
  }

  @Override
  public PersonDTO insert(@Nonnull PersonDTO personDTO) throws DAOException, DataSourceException {
    long resultId;
    LocationDTO locationDTO;
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, personDTO.ownerId);
      preparedStatement.setString(2, personDTO.passportId);
      preparedStatement.setString(3, eyeColorAdapter.to(personDTO.eyeColor));
      preparedStatement.setString(4, hairColorAdapter.to(personDTO.hairColor));

      if (personDTO.locationDTO != null) {
        locationDTO = locationDAO.insert(personDTO.locationDTO);
        preparedStatement.setDouble(5, locationDTO.id);
      } else {
        locationDTO = null;
        preparedStatement.setNull(5, Types.INTEGER);
      }

      preparedStatement.execute();

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        resultId = generatedKeys.getLong(1);
      } else {
        logger.error(() -> "Cannot get generated person id.");
        throw new DAOException(CANNOT_GET_GENERATED_PERSON_ID_EXCEPTION);
      }
    } catch (SQLException | AdapterException e) {
      logger.error(() -> "Cannot insert person.", e);
      throw new DAOException(CANNOT_INSERT_PERSON_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Person was inserted.");
    return new PersonDTO(
        resultId,
        personDTO.ownerId,
        personDTO.passportId,
        personDTO.eyeColor,
        personDTO.hairColor,
        locationDTO);
  }

  @Override
  public PersonDTO update(@Nonnull PersonDTO personDTO) throws DAOException, DataSourceException {
    PersonDTO previous = getByKey(personDTO.id);
    LocationDTO locationDTO;
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(UPDATE, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, personDTO.ownerId);
      preparedStatement.setString(2, personDTO.passportId);
      preparedStatement.setString(3, eyeColorAdapter.to(personDTO.eyeColor));
      preparedStatement.setString(4, hairColorAdapter.to(personDTO.hairColor));

      if (previous.locationDTO == null && personDTO.locationDTO != null) {
        locationDTO = locationDAO.insert(personDTO.locationDTO);
        preparedStatement.setLong(5, locationDTO.id);
      } else if (previous.locationDTO != null && personDTO.locationDTO == null) {
        locationDTO = null;
        preparedStatement.setNull(5, Types.INTEGER);
      } else if (previous.locationDTO != null) {
        locationDTO = locationDAO.update(personDTO.locationDTO);
        preparedStatement.setLong(5, locationDTO.id);
      } else {
        locationDTO = null;
        preparedStatement.setNull(5, Types.INTEGER);
      }

      preparedStatement.setLong(6, personDTO.id);

      preparedStatement.execute();

      if (previous.locationDTO != null && personDTO.locationDTO == null) {
        locationDAO.delete(previous.locationDTO);
      }
    } catch (SQLException | AdapterException e) {
      logger.error(() -> "Cannot update person.", e);
      throw new DAOException(CANNOT_UPDATE_PERSON_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Person was updated.");
    return new PersonDTO(
        personDTO.id,
        personDTO.ownerId,
        personDTO.passportId,
        personDTO.eyeColor,
        personDTO.hairColor,
        locationDTO);
  }

  @Override
  public void delete(@Nonnull PersonDTO personDTO) throws DAOException, DataSourceException {
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(DELETE, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, personDTO.id);

      preparedStatement.execute();

      if (personDTO.locationDTO != null) {
        locationDAO.delete(personDTO.locationDTO);
      }
    } catch (SQLException e) {
      logger.error(() -> "Cannot delete person.", e);
      throw new DAOException(CANNOT_DELETE_PERSON_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Person was deleted.");
  }
}
