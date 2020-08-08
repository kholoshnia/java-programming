package ru.lab.server.commands;

import ru.lab.common.reply.Reply;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;

/** Abstract class for creating commands */
public interface Command {
  /**
   * Returns key for the command
   *
   * @return Key for the command
   */
  String getKey();

  /**
   * Returns info for help command
   *
   * @return Info for help command
   */
  String getInfo();

  /**
   * Returns parameters for the command
   *
   * @return Parameters for the command
   */
  String getParameters();

  /**
   * Returns reply about execution of the command
   *
   * @param editor editor with data
   * @return Reply about execution of the command
   */
  Reply execute(Editor editor) throws ExecutorException;
}
