package ru.lab.commands;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

/** Available commands class */
public class AvailableCommands {
  private HashMap<String, Command> commands;

  public AvailableCommands() {
    commands = new HashMap<>();

    Set<Class<? extends Command>> classes =
        new Reflections(Command.class).getSubTypesOf(Command.class);

    for (Class<? extends Command> el : classes) {
      try {
        Command com = (Command) Class.forName(el.getName()).getConstructor().newInstance();
        commands.put(com.getKey(), com);
      } catch (ReflectiveOperationException ignored) {
      }
    }
  }

  /**
   * Returns available commands
   *
   * @return Available commands
   */
  public HashMap<String, Command> getCommands() {
    return commands;
  }

  /**
   * Returns command by its key
   *
   * @param key key to get command
   * @return Command by key
   */
  public Command getCommand(String key) {
    return commands.get(key);
  }
}
