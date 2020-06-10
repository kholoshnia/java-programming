package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.element.Worker;
import com.lab.runner.Editor;
import com.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;

/** Remove lower command class */
public final class RemoveLowerCommand implements Command {
  @Override
  public String getKey() {
    return "remove_lower";
  }

  @Override
  public String getInfo() {
    return "удалить из коллекции все элементы, меньшие, чем заданный";
  }

  @Override
  public String getParameters() {
    return "{element}";
  }

  /**
   * Removes all elements smaller than the specified from the collection
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      Worker worker = editor.create();
      if (worker == null) {
        return new Response(false, response, Response.Types.FAIL);
      }
      response.add("Удлаение из коллекции всех элементов, меньше, чем заданный");
      editor
          .getCollection()
          .getEntrySet()
          .removeIf(entry -> entry.getValue().compareTo(worker) < 0);
      return new Response(true, response, Response.Types.CORRECT);
    } else {
      System.out.println("В коллекции нет элементов");
    }
    return new Response(false, response, Response.Types.UNNECESSARY);
  }
}
