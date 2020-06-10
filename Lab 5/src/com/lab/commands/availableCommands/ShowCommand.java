package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.console.Output;
import com.lab.element.Worker;
import com.lab.runner.Editor;
import com.lab.runner.Response;

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
