package ru.lab.server.model.dao.daos;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.server.model.dao.DAO;
import ru.lab.server.model.dao.exceptions.DAOException;
import ru.lab.server.model.domain.dto.dtos.LocationDTO;
import ru.lab.server.model.source.DataSource;
import ru.lab.server.model.source.exceptions.DataSourceException;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LocationDAO implements DAO<Long, LocationDTO> {
  private static final Logger logger = LogManager.getLogger(LocationDAO.class);

  private static final String SELECT_ALL = "SELECT * FROM " + LocationDTO.TABLE_NAME;

  private static final String SELECT_BY_ID =
      SELECT_ALL + " WHERE " + LocationDTO.ID_COLUMN + " = ?";

  private static final String INSERT =
      "INSERT INTO "
          + LocationDTO.TABLE_NAME
          + " ("
          + LocationDTO.OWNER_ID_COLUMN
          + ", "
          + LocationDTO.X_COLUMN
          + ", "
          + LocationDTO.Y_COLUMN
          + ", "
          + LocationDTO.Z_COLUMN
          + ", "
          + LocationDTO.NAME_COLUMN
          + ") VALUES (?, ?, ?, ?, ?)";

  private static final String UPDATE =
      "UPDATE "
          + LocationDTO.TABLE_NAME
          + " SET "
          + LocationDTO.OWNER_ID_COLUMN
          + " = ?, "
          + LocationDTO.X_COLUMN
          + " = ?, "
          + LocationDTO.Y_COLUMN
          + " = ?, "
          + LocationDTO.Z_COLUMN
          + " = ?, "
          + LocationDTO.NAME_COLUMN
          + " = ? WHERE "
          + LocationDTO.ID_COLUMN
          + " = ?";

  private static final String DELETE =
      "DELETE FROM " + LocationDTO.TABLE_NAME + " WHERE " + LocationDTO.ID_COLUMN + " = ?";

  private static final String CANNOT_GET_ALL_LOCATIONS_EXCEPTION;
  private static final String CANNOT_GET_LOCATION_BY_ID_EXCEPTION;
  private static final String CANNOT_INSERT_LOCATION_EXCEPTION;
  private static final String CANNOT_GET_GENERATED_LOCATION_ID_EXCEPTION;
  private static final String CANNOT_UPDATE_LOCATION_EXCEPTION;
  private static final String CANNOT_DELETE_LOCATION_EXCEPTION;

  static {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("internal.LocationDAO");

    CANNOT_GET_ALL_LOCATIONS_EXCEPTION =
        resourceBundle.getString("exceptions.cannotGetAllLocations");
    CANNOT_GET_LOCATION_BY_ID_EXCEPTION =
        resourceBundle.getString("exceptions.cannotGetLocationById");
    CANNOT_INSERT_LOCATION_EXCEPTION = resourceBundle.getString("exceptions.cannotInsertLocation");
    CANNOT_GET_GENERATED_LOCATION_ID_EXCEPTION =
        resourceBundle.getString("exceptions.cannotGetGeneratedLocationId");
    CANNOT_UPDATE_LOCATION_EXCEPTION = resourceBundle.getString("exceptions.cannotUpdateLocation");
    CANNOT_DELETE_LOCATION_EXCEPTION = resourceBundle.getString("exceptions.cannotDeleteLocation");
  }

  private final DataSource dataSource;

  @Inject
  public LocationDAO(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<LocationDTO> getAll() throws DAOException, DataSourceException {
    List<LocationDTO> allLocationDTOs = new ArrayList<>();
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(SELECT_ALL, Statement.NO_GENERATED_KEYS);

    try {
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        long id = resultSet.getLong(LocationDTO.ID_COLUMN);
        long ownerId = resultSet.getLong(LocationDTO.OWNER_ID_COLUMN);
        Long x = resultSet.getObject(LocationDTO.X_COLUMN, Long.class);
        long y = resultSet.getLong(LocationDTO.Y_COLUMN);
        Double z = resultSet.getObject(LocationDTO.Z_COLUMN, Double.class);
        String name = resultSet.getObject(LocationDTO.NAME_COLUMN, String.class);

        LocationDTO locationDTO = new LocationDTO(id, ownerId, x, y, z, name);
        allLocationDTOs.add(locationDTO);
      }
    } catch (SQLException e) {
      logger.error(() -> "Cannot get all locations.", e);
      throw new DAOException(CANNOT_GET_ALL_LOCATIONS_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Got all locations.");
    return allLocationDTOs;
  }

  @Override
  public LocationDTO getByKey(@Nonnull Long id) throws DAOException, DataSourceException {
    LocationDTO locationDTO = null;
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(SELECT_BY_ID, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        long ownerId = resultSet.getLong(LocationDTO.OWNER_ID_COLUMN);
        Long x = resultSet.getObject(LocationDTO.X_COLUMN, Long.class);
        long y = resultSet.getLong(LocationDTO.Y_COLUMN);
        Double z = resultSet.getObject(LocationDTO.Z_COLUMN, Double.class);
        String name = resultSet.getObject(LocationDTO.NAME_COLUMN, String.class);

        locationDTO = new LocationDTO(id, ownerId, x, y, z, name);
      }
    } catch (SQLException e) {
      logger.error(() -> "Cannot get location by id.", e);
      throw new DAOException(CANNOT_GET_LOCATION_BY_ID_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Got location by id.");
    return locationDTO;
  }

  @Override
  public LocationDTO insert(@Nonnull LocationDTO locationDTO)
      throws DAOException, DataSourceException {
    long resultId;
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, locationDTO.ownerId);

      if (locationDTO.x != null) {
        preparedStatement.setLong(2, locationDTO.x);
      } else {
        preparedStatement.setNull(2, Types.BIGINT);
      }

      preparedStatement.setLong(3, locationDTO.y);

      if (locationDTO.z != null) {
        preparedStatement.setDouble(4, locationDTO.z);
      } else {
        preparedStatement.setDouble(4, Types.DOUBLE);
      }

      preparedStatement.setString(5, locationDTO.name);

      preparedStatement.execute();

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        resultId = generatedKeys.getLong(1);
      } else {
        logger.error(() -> "Cannot get generated location id.");
        throw new DAOException(CANNOT_GET_GENERATED_LOCATION_ID_EXCEPTION);
      }
    } catch (SQLException e) {
      logger.error(() -> "Cannot insert location.", e);
      throw new DAOException(CANNOT_INSERT_LOCATION_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Location was inserted.");
    return new LocationDTO(
        resultId,
        locationDTO.ownerId,
        locationDTO.x,
        locationDTO.y,
        locationDTO.z,
        locationDTO.name);
  }

  @Override
  public LocationDTO update(@Nonnull LocationDTO locationDTO)
      throws DAOException, DataSourceException {
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(UPDATE, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, locationDTO.ownerId);

      if (locationDTO.x != null) {
        preparedStatement.setLong(2, locationDTO.x);
      } else {
        preparedStatement.setNull(2, Types.BIGINT);
      }

      preparedStatement.setLong(3, locationDTO.y);

      if (locationDTO.z != null) {
        preparedStatement.setDouble(4, locationDTO.z);
      } else {
        preparedStatement.setDouble(4, Types.DOUBLE);
      }

      preparedStatement.setString(5, locationDTO.name);

      preparedStatement.setLong(6, locationDTO.id);

      preparedStatement.execute();
    } catch (SQLException e) {
      logger.error(() -> "Cannot update location.", e);
      throw new DAOException(CANNOT_UPDATE_LOCATION_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Location was updated.");
    return new LocationDTO(
        locationDTO.id,
        locationDTO.ownerId,
        locationDTO.x,
        locationDTO.y,
        locationDTO.z,
        locationDTO.name);
  }

  @Override
  public void delete(@Nonnull LocationDTO locationDTO) throws DAOException, DataSourceException {
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(DELETE, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, locationDTO.id);

      preparedStatement.execute();
    } catch (SQLException e) {
      logger.error(() -> "Cannot delete location.", e);
      throw new DAOException(CANNOT_DELETE_LOCATION_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Location was deleted.");
  }
}
