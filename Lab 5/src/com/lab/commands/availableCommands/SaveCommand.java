package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.runner.Editor;
import com.lab.runner.Response;

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
