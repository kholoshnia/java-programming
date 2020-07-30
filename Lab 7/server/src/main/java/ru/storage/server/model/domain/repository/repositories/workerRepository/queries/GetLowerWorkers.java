package ru.storage.server.model.domain.repository.repositories.workerRepository.queries;

import ru.storage.server.model.domain.entity.entities.worker.Worker;
import ru.storage.server.model.domain.repository.Query;

import java.util.List;
import java.util.stream.Collectors;

/** Returns workers collection copy. The copy contains a workers lower than the specified. */
public final class GetLowerWorkers implements Query<Worker> {
  private final Worker worker;

  /**
   * Creates a query to get workers lower than the specified.
   *
   * @param worker target worker
   */
  public GetLowerWorkers(Worker worker) {
    this.worker = worker;
  }

  @Override
  public List<Worker> execute(List<Worker> workers) {
    return workers.stream()
        .filter(worker -> worker.compareTo(this.worker) < 0)
        .collect(Collectors.toList());
  }
}
