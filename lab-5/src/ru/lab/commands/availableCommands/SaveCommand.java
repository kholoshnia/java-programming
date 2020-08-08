package ru.lab.commands.availableCommands;

import ru.lab.commands.Command;
import ru.lab.runner.Editor;
import ru.lab.runner.Response;

/** Save command class */
public final class SaveCommand implements Command {
  @Override
  public String getKey() {
    return "save";
  }

  @Override
  public String getInfo() {
    return "сохранить коллекцию в файл";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Saves collection to file
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    return editor.save();
  }
}
