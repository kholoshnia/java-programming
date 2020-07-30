package ru.storage.server.model.domain.repository.repositories.workerRepository.queries;

import ru.storage.server.model.domain.entity.entities.worker.Worker;
import ru.storage.server.model.domain.repository.Query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Returns workers collection copy. The copy contains a workers with a least name */
public final class GetMinNameWorkers implements Query<Worker> {
  @Override
  public List<Worker> execute(List<Worker> workers) {
    Worker worker = workers.stream().min(Comparator.comparing(Worker::getName)).orElse(null);

    if (worker == null) {
      return new ArrayList<>();
    }

    return new ArrayList<Worker>() {
      {
        add(worker);
      }
    };
  }
}
