package ru.lab.server.model.domain.repository.repositories.workerRepository.queries;

import ru.lab.server.model.domain.entity.entities.worker.Worker;
import ru.lab.server.model.domain.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Returns workers collection copy. The copy contains a workers with a start date less than the
 * specified.
 */
public final class GetLessStartDateWorkers implements Query<Worker> {
  private final Date startDate;

  /**
   * Creates a query to get workers with the start date less than the specified.
   *
   * @param startDate worker start date
   */
  public GetLessStartDateWorkers(Date startDate) {
    this.startDate = startDate;
  }

  @Override
  public List<Worker> execute(List<Worker> workers) {
    return workers.stream()
        .filter(worker -> worker.getStartDate().compareTo(startDate) < 0)
        .collect(Collectors.toList());
  }
}
