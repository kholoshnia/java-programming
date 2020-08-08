package ru.lab.commands;

import java.util.ArrayList;
import java.util.List;

/** History of commands that executed correctly */
public final class CommandsHistory {
  private List<Command> commands;

  public CommandsHistory() {
    commands = new ArrayList<>();
  }

  /**
   * Returns commands history
   *
   * @return Commands history
   */
  public List<Command> getCommands() {
    return commands;
  }

  /**
   * Adds command to history
   *
   * @param command command
   */
  public void addCommand(Command command) {
    commands.add(command);
  }
}
