package ru.lab.commands.availableCommands;

import ru.lab.commands.Command;
import ru.lab.console.Output;
import ru.lab.element.Worker;
import ru.lab.runner.Editor;
import ru.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Show command class */
public final class ShowCommand implements Command {
  @Override
  public String getKey() {
    return "show";
  }

  @Override
  public String getInfo() {
    return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Outputs to the standard output stream all the elements of the collection in a string
   * representation
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      response.add(
          Output.Colors.CYAN.getColorCode()
              + "Элементы коллекции в строковом представлении: "
              + Output.Colors.RESET.getColorCode());
      for (Map.Entry<Integer, Worker> el : editor.getCollection().getEntrySet()) {
        response.add(
            Output.Colors.BLUE.getColorCode()
                + Output.Decorators.BOLD.getDecoratorCode()
                + Output.Decorators.UNDERLINE.getDecoratorCode()
                + el.getKey().toString()
                + Output.Decorators.RESET.getDecoratorCode()
                + ": { "
                + el.getValue().toString()
                + " }");
      }
      return new Response(true, response, Response.Types.TEXT);
    } else {
      response.add("В коллекции нет элементов");
    }
    return new Response(false, response, Response.Types.UNNECESSARY);
  }
}
