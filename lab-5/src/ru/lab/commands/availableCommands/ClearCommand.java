package ru.lab.commands.availableCommands;

import ru.lab.commands.Command;
import ru.lab.runner.Editor;
import ru.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;

/** Clear command class */
public final class ClearCommand implements Command {
  @Override
  public String getKey() {
    return "clear";
  }

  @Override
  public String getInfo() {
    return "очистить коллекцию";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Clears the collection
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      editor.getCollection().clear();
      response.add("Коллекция очищена");
      return new Response(true, response, Response.Types.CORRECT);
    } else {
      response.add("В коллекции нет элементов");
    }
    return new Response(false, response, Response.Types.UNNECESSARY);
  }
}
