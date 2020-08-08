package ru.lab.commands.availableCommands;

import ru.lab.commands.Command;
import ru.lab.element.Worker;
import ru.lab.runner.Editor;
import ru.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Min by name command class */
public final class MinByNameCommand implements Command {
  @Override
  public String getKey() {
    return "min_by_name";
  }

  @Override
  public String getInfo() {
    return "вывести любой объект из коллекции, значение поля name которого является минимальным";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Shows an item with a minimum name value
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      Worker minNameWorker = editor.getCollection().getEntrySet().iterator().next().getValue();
      for (Map.Entry<Integer, Worker> el : editor.getCollection().getEntrySet()) {
        if (el.getValue().getName().compareTo(minNameWorker.getName()) < 0) {
          minNameWorker = el.getValue();
        }
      }
      response.add("Элемент с миниальным именем: " + minNameWorker);
      return new Response(true, response, Response.Types.TEXT);
    } else {
      response.add("В коллекции нет элементов");
    }
    return new Response(false, response, Response.Types.UNNECESSARY);
  }
}
