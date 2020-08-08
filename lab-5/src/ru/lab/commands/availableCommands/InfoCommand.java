package ru.lab.commands.availableCommands;

import ru.lab.commands.Command;
import ru.lab.runner.Editor;
import ru.lab.runner.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/** Info command class */
public final class InfoCommand implements Command {
  @Override
  public String getKey() {
    return "info";
  }

  @Override
  public String getInfo() {
    return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Shows info about collection
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    response.add("Тип коллекции: " + editor.getCollection().getCollectionType());
    response.add(
        "Дата инициализации коллекции: "
            + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                .format((editor.getCollection().getInitTime())));
    response.add("Количество элементов коллекции: " + editor.getCollection().getSize());
    return new Response(true, response, Response.Types.TEXT);
  }
}
