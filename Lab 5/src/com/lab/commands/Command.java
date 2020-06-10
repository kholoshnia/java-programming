package com.lab.commands;

import com.lab.runner.Editor;
import com.lab.runner.Response;

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
   * Returns response about execution of the command
   *
   * @param editor editor with data
   * @return Response about execution of the command
   */
  Response execute(Editor editor);
}
