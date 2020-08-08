package ru.lab.server.model.dao.daos;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.server.model.dao.DAO;
import ru.lab.server.model.dao.adapter.Adapter;
import ru.lab.server.model.dao.adapter.exceptions.AdapterException;
import ru.lab.server.model.dao.exceptions.DAOException;
import ru.lab.server.model.domain.dto.dtos.CoordinatesDTO;
import ru.lab.server.model.domain.dto.dtos.PersonDTO;
import ru.lab.server.model.domain.dto.dtos.WorkerDTO;
import ru.lab.server.model.domain.entity.entities.worker.Status;
import ru.lab.server.model.source.DataSource;
import ru.lab.server.model.source.exceptions.DataSourceException;

import javax.annotation.Nonnull;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class WorkerDAO implements DAO<Long, WorkerDTO> {
  private static final Logger logger = LogManager.getLogger(WorkerDAO.class);

  private static final String SELECT_ALL = "SELECT * FROM " + WorkerDTO.TABLE_NAME;

  private static final String SELECT_BY_ID = SELECT_ALL + " WHERE " + WorkerDTO.ID_COLUMN + " = ?";

  private static final String INSERT =
      "INSERT INTO "
          + WorkerDTO.TABLE_NAME
          + " ("
          + WorkerDTO.OWNER_ID_COLUMN
          + ", "
          + WorkerDTO.KEY_COLUMN
          + ", "
          + WorkerDTO.NAME_COLUMN
          + ", "
          + WorkerDTO.COORDINATES_COLUMN
          + ", "
          + WorkerDTO.CREATION_DATE_COLUMN
          + ", "
          + WorkerDTO.SALARY_COLUMN
          + ", "
          + WorkerDTO.START_DATE_COLUMN
          + ", "
          + WorkerDTO.END_DATE_COLUMN
          + ", "
          + WorkerDTO.STATUS_COLUMN
          + ", "
          + WorkerDTO.PERSON_COLUMN
          + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String UPDATE =
      "UPDATE "
          + WorkerDTO.TABLE_NAME
          + " SET "
          + WorkerDTO.OWNER_ID_COLUMN
          + " = ?, "
          + WorkerDTO.KEY_COLUMN
          + " = ?, "
          + WorkerDTO.NAME_COLUMN
          + " = ?, "
          + WorkerDTO.COORDINATES_COLUMN
          + " = ?, "
          + WorkerDTO.CREATION_DATE_COLUMN
          + " = ?, "
          + WorkerDTO.SALARY_COLUMN
          + " = ?, "
          + WorkerDTO.START_DATE_COLUMN
          + " = ?, "
          + WorkerDTO.END_DATE_COLUMN
          + " = ?, "
          + WorkerDTO.STATUS_COLUMN
          + " = ?, "
          + WorkerDTO.PERSON_COLUMN
          + " = ? WHERE "
          + WorkerDTO.ID_COLUMN
          + " = ?";

  private static final String DELETE =
      "DELETE FROM " + WorkerDTO.TABLE_NAME + " WHERE " + WorkerDTO.ID_COLUMN + " = ?";

  private static final String CANNOT_GET_ALL_WORKERS_EXCEPTION;
  private static final String CANNOT_GET_WORKER_BY_ID_EXCEPTION;
  private static final String CANNOT_INSERT_WORKER_EXCEPTION;
  private static final String CANNOT_GET_GENERATED_WORKER_ID_EXCEPTION;
  private static final String CANNOT_UPDATE_WORKER_EXCEPTION;
  private static final String CANNOT_DELETE_WORKER_EXCEPTION;

  static {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("internal.WorkerDAO");

    CANNOT_GET_ALL_WORKERS_EXCEPTION = resourceBundle.getString("exceptions.cannotGetAllWorkers");
    CANNOT_GET_WORKER_BY_ID_EXCEPTION = resourceBundle.getString("exceptions.cannotGetWorkerById");
    CANNOT_INSERT_WORKER_EXCEPTION = resourceBundle.getString("exceptions.cannotInsertWorker");
    CANNOT_GET_GENERATED_WORKER_ID_EXCEPTION =
        resourceBundle.getString("exceptions.cannotGetGeneratedWorkerId");
    CANNOT_UPDATE_WORKER_EXCEPTION = resourceBundle.getString("exceptions.cannotUpdateWorker");
    CANNOT_DELETE_WORKER_EXCEPTION = resourceBundle.getString("exceptions.cannotDeleteWorker");
  }

  private final DataSource dataSource;
  private final Adapter<Status, String> statusAdapter;
  private final Adapter<Date, Timestamp> dateAdapter;
  private final Adapter<LocalDate, java.sql.Date> localDateAdapter;
  private final Adapter<LocalDateTime, Timestamp> localDateTimeAdapter;
  private final DAO<Long, CoordinatesDTO> coordinatesDAO;
  private final DAO<Long, PersonDTO> personDAO;

  @Inject
  public WorkerDAO(
      DataSource dataSource,
      Adapter<Status, String> statusAdapter,
      Adapter<Date, Timestamp> dateAdapter,
      Adapter<LocalDate, java.sql.Date> localDateAdapter,
      Adapter<LocalDateTime, Timestamp> localDateTimeAdapter,
      DAO<Long, CoordinatesDTO> coordinatesDAO,
      DAO<Long, PersonDTO> personDAO) {
    this.dataSource = dataSource;
    this.statusAdapter = statusAdapter;
    this.dateAdapter = dateAdapter;
    this.localDateAdapter = localDateAdapter;
    this.localDateTimeAdapter = localDateTimeAdapter;
    this.coordinatesDAO = coordinatesDAO;
    this.personDAO = personDAO;
  }

  @Override
  public List<WorkerDTO> getAll() throws DAOException, DataSourceException {
    List<WorkerDTO> allWorkerDTOs = new ArrayList<>();
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(SELECT_ALL, Statement.NO_GENERATED_KEYS);

    try {
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        long id = resultSet.getLong(WorkerDTO.ID_COLUMN);
        long ownerId = resultSet.getLong(WorkerDTO.OWNER_ID_COLUMN);
        int key = resultSet.getInt(WorkerDTO.KEY_COLUMN);
        String name = resultSet.getObject(WorkerDTO.NAME_COLUMN, String.class);

        long coordinatesId = resultSet.getLong(WorkerDTO.COORDINATES_COLUMN);
        CoordinatesDTO coordinatesDTO = coordinatesDAO.getByKey(coordinatesId);

        LocalDate creationDate =
            localDateAdapter.from(
                resultSet.getObject(WorkerDTO.CREATION_DATE_COLUMN, java.sql.Date.class));
        double salary = resultSet.getDouble(WorkerDTO.SALARY_COLUMN);
        Date startDate =
            dateAdapter.from(resultSet.getObject(WorkerDTO.START_DATE_COLUMN, Timestamp.class));
        LocalDateTime endDate =
            localDateTimeAdapter.from(
                resultSet.getObject(WorkerDTO.END_DATE_COLUMN, Timestamp.class));
        Status status =
            statusAdapter.from(resultSet.getObject(WorkerDTO.STATUS_COLUMN, String.class));

        long personId = resultSet.getLong(WorkerDTO.PERSON_COLUMN);
        PersonDTO personDTO = personDAO.getByKey(personId);

        WorkerDTO workerDTO =
            new WorkerDTO(
                id,
                ownerId,
                key,
                name,
                coordinatesDTO,
                creationDate,
                salary,
                startDate,
                endDate,
                status,
                personDTO);
        allWorkerDTOs.add(workerDTO);
      }
    } catch (SQLException | AdapterException e) {
      logger.error(() -> "Cannot get all workers.", e);
      throw new DAOException(CANNOT_GET_ALL_WORKERS_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Got all workers.");
    return allWorkerDTOs;
  }

  @Override
  public WorkerDTO getByKey(@Nonnull Long id) throws DAOException, DataSourceException {
    WorkerDTO workerDTO = null;
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(SELECT_BY_ID, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        long ownerId = resultSet.getLong(WorkerDTO.OWNER_ID_COLUMN);
        int key = resultSet.getInt(WorkerDTO.KEY_COLUMN);
        String name = resultSet.getObject(WorkerDTO.NAME_COLUMN, String.class);

        long coordinatesId = resultSet.getLong(WorkerDTO.COORDINATES_COLUMN);
        CoordinatesDTO coordinatesDTO = coordinatesDAO.getByKey(coordinatesId);

        LocalDate creationDate =
            localDateAdapter.from(
                resultSet.getObject(WorkerDTO.CREATION_DATE_COLUMN, java.sql.Date.class));
        double salary = resultSet.getDouble(WorkerDTO.SALARY_COLUMN);
        Date startDate =
            dateAdapter.from(resultSet.getObject(WorkerDTO.START_DATE_COLUMN, Timestamp.class));
        LocalDateTime endDate =
            localDateTimeAdapter.from(
                resultSet.getObject(WorkerDTO.END_DATE_COLUMN, Timestamp.class));
        Status status =
            statusAdapter.from(resultSet.getObject(WorkerDTO.STATUS_COLUMN, String.class));

        long personId = resultSet.getLong(WorkerDTO.PERSON_COLUMN);
        PersonDTO personDTO = personDAO.getByKey(personId);

        workerDTO =
            new WorkerDTO(
                id,
                ownerId,
                key,
                name,
                coordinatesDTO,
                creationDate,
                salary,
                startDate,
                endDate,
                status,
                personDTO);
      }
    } catch (SQLException | AdapterException e) {
      logger.error(() -> "Cannot get worker by id.", e);
      throw new DAOException(CANNOT_GET_WORKER_BY_ID_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Got worker by id.");
    return workerDTO;
  }

  @Override
  public WorkerDTO insert(@Nonnull WorkerDTO workerDTO) throws DAOException, DataSourceException {
    long resultId;
    CoordinatesDTO coordinatesDTO;
    PersonDTO personDTO;
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, workerDTO.ownerId);
      preparedStatement.setInt(2, workerDTO.key);
      preparedStatement.setString(3, workerDTO.name);

      if (workerDTO.coordinatesDTO != null) {
        coordinatesDTO = coordinatesDAO.insert(workerDTO.coordinatesDTO);
        preparedStatement.setLong(4, coordinatesDTO.id);
      } else {
        coordinatesDTO = null;
        preparedStatement.setNull(4, Types.INTEGER);
      }

      preparedStatement.setDate(5, localDateAdapter.to(workerDTO.creationDate));
      preparedStatement.setDouble(6, workerDTO.salary);
      preparedStatement.setTimestamp(7, dateAdapter.to(workerDTO.startDate));
      preparedStatement.setTimestamp(8, localDateTimeAdapter.to(workerDTO.endDate));
      preparedStatement.setString(9, statusAdapter.to(workerDTO.status));

      if (workerDTO.personDTO != null) {
        personDTO = personDAO.insert(workerDTO.personDTO);
        preparedStatement.setLong(10, personDTO.id);
      } else {
        personDTO = null;
        preparedStatement.setNull(10, Types.INTEGER);
      }

      preparedStatement.execute();

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        resultId = generatedKeys.getLong(1);
      } else {
        logger.error(() -> "Cannot get generated worker id.");
        throw new DAOException(CANNOT_GET_GENERATED_WORKER_ID_EXCEPTION);
      }
    } catch (SQLException | AdapterException e) {
      logger.error(() -> "Cannot insert worker.", e);
      throw new DAOException(CANNOT_INSERT_WORKER_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Worker was inserted.");
    return new WorkerDTO(
        resultId,
        workerDTO.ownerId,
        workerDTO.key,
        workerDTO.name,
        coordinatesDTO,
        workerDTO.creationDate,
        workerDTO.salary,
        workerDTO.startDate,
        workerDTO.endDate,
        workerDTO.status,
        personDTO);
  }

  @Override
  public WorkerDTO update(@Nonnull WorkerDTO workerDTO) throws DAOException, DataSourceException {
    WorkerDTO previous = getByKey(workerDTO.id);
    CoordinatesDTO coordinatesDTO;
    PersonDTO personDTO;
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(UPDATE, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, workerDTO.ownerId);
      preparedStatement.setInt(2, workerDTO.key);
      preparedStatement.setString(3, workerDTO.name);

      if (previous.coordinatesDTO == null && workerDTO.coordinatesDTO != null) {
        coordinatesDTO = coordinatesDAO.insert(workerDTO.coordinatesDTO);
        preparedStatement.setLong(4, coordinatesDTO.id);
      } else if (previous.coordinatesDTO != null && workerDTO.coordinatesDTO == null) {
        coordinatesDTO = null;
        preparedStatement.setNull(4, Types.INTEGER);
      } else if (previous.coordinatesDTO != null) {
        coordinatesDTO = coordinatesDAO.update(workerDTO.coordinatesDTO);
        preparedStatement.setLong(4, coordinatesDTO.id);
      } else {
        coordinatesDTO = null;
        preparedStatement.setNull(4, Types.INTEGER);
      }

      preparedStatement.setDate(5, localDateAdapter.to(workerDTO.creationDate));
      preparedStatement.setDouble(6, workerDTO.salary);
      preparedStatement.setTimestamp(7, dateAdapter.to(workerDTO.startDate));
      preparedStatement.setTimestamp(8, localDateTimeAdapter.to(workerDTO.endDate));
      preparedStatement.setString(9, statusAdapter.to(workerDTO.status));

      if (previous.personDTO == null && workerDTO.personDTO != null) {
        personDTO = personDAO.insert(workerDTO.personDTO);
        preparedStatement.setLong(10, personDTO.id);
      } else if (previous.personDTO != null && workerDTO.personDTO == null) {
        personDTO = null;
        preparedStatement.setNull(10, Types.INTEGER);
      } else if (previous.personDTO != null) {
        personDTO = personDAO.update(workerDTO.personDTO);
        preparedStatement.setLong(10, personDTO.id);
      } else {
        personDTO = null;
        preparedStatement.setNull(10, Types.INTEGER);
      }

      preparedStatement.setLong(11, workerDTO.id);

      preparedStatement.execute();

      if (previous.coordinatesDTO != null && workerDTO.coordinatesDTO == null) {
        coordinatesDAO.delete(previous.coordinatesDTO);
      }

      if (previous.personDTO != null && workerDTO.personDTO == null) {
        personDAO.delete(previous.personDTO);
      }
    } catch (SQLException | AdapterException e) {
      logger.error(() -> "Cannot update worker.", e);
      throw new DAOException(CANNOT_UPDATE_WORKER_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Worker was updated.");
    return new WorkerDTO(
        workerDTO.id,
        workerDTO.ownerId,
        workerDTO.key,
        workerDTO.name,
        coordinatesDTO,
        workerDTO.creationDate,
        workerDTO.salary,
        workerDTO.startDate,
        workerDTO.endDate,
        workerDTO.status,
        personDTO);
  }

  @Override
  public void delete(@Nonnull WorkerDTO workerDTO) throws DAOException, DataSourceException {
    PreparedStatement preparedStatement =
        dataSource.getPrepareStatement(DELETE, Statement.NO_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, workerDTO.id);

      preparedStatement.execute();

      if (workerDTO.coordinatesDTO != null) {
        coordinatesDAO.delete(workerDTO.coordinatesDTO);
      }

      if (workerDTO.personDTO != null) {
        personDAO.delete(workerDTO.personDTO);
      }

    } catch (SQLException e) {
      logger.error(() -> "Cannot delete worker.", e);
      throw new DAOException(CANNOT_DELETE_WORKER_EXCEPTION, e);
    } finally {
      dataSource.closePrepareStatement(preparedStatement);
    }

    logger.info(() -> "Worker was deleted.");
  }
}
