package ru.lab.common.exitManager;

import ru.lab.common.exitManager.exceptions.ExitingException;

public interface ExitListener {
  /**
   * Exits the specified process.
   *
   * @throws ExitingException - in case of exceptions while exiting
   */
  void exit() throws ExitingException;
}
