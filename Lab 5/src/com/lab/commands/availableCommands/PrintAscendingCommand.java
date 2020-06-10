package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.console.Output;
import com.lab.runner.Editor;
import com.lab.runner.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Print ascending command class */
public final class PrintAscendingCommand implements Command {
  @Override
  public String getKey() {
    return "print_ascending";
  }

  @Override
  public String getInfo() {
    return "вывести элементы коллекции в порядке возрастания";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Print collection elements in ascending order
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      response.add(
          Output.Colors.CYAN.getColorCode()
              + "Элементы коллекции по возрастанию: "
              + Output.Colors.RESET.getColorCode());
      List<Integer> keys = new ArrayList<>(editor.getCollection().keySet());
      Collections.sort(keys);
      for (int key : keys) {
        response.add(
            Output.Colors.BLUE.getColorCode()
                + Output.Decorators.BOLD.getDecoratorCode()
                + Output.Decorators.UNDERLINE.getDecoratorCode()
                + key
                + Output.Decorators.RESET.getDecoratorCode()
                + ": { "
                + editor.getCollection().get(key)
                + " }");
      }
      return new Response(true, response, Response.Types.TEXT);
    } else {
      response.add("В коллекции нет элементов");
    }
    return new Response(false, response, Response.Types.UNNECESSARY);
  }
}
