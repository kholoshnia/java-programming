package ru.lab.client.view;

import ru.lab.common.exitManager.exceptions.ExitingException;

public interface View {
  /**
   * Processes user interface view.
   *
   * @throws ExitingException - in case of exceptions while exciting
   */
  void process() throws ExitingException;
}
