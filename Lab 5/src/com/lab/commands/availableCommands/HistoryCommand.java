package com.lab.commands.availableCommands;

import com.lab.commands.AvailableCommands;
import com.lab.commands.Command;
import com.lab.console.Output;
import com.lab.runner.Editor;
import com.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** History command class */
public final class HistoryCommand implements Command {
  @Override
  public String getKey() {
    return "history";
  }

  @Override
  public String getInfo() {
    return "вывести последние 9 команд (без их аргументов)";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Shows history for last 9 commands
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getCommandHistory().getCommands().size() > 0) {
      response.add(
          Output.Decorators.BOLD.getDecoratorCode()
              + "История комманд: "
              + Output.Decorators.RESET.getDecoratorCode());
      int k = 0;
      for (int i = editor.getCommandHistory().getCommands().size() - 1; i >= 0; i--) {
        for (Map.Entry<String, Command> el : new AvailableCommands().getCommands().entrySet()) {
          if (editor
              .getCommandHistory()
              .getCommands()
              .get(i)
              .getClass()
              .equals(el.getValue().getClass())) {
            k++;
            response.add(el.getKey());
          }
        }
        if (k >= 9) {
          break;
        }
      }
      return new Response(true, response, Response.Types.TEXT);
    } else {
      response.add("История комманд отсутствует");
    }
    return new Response(false, response, Response.Types.UNNECESSARY);
  }
}
